package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import io.woo.htmltopdf.HtmlToPdf;
import io.woo.htmltopdf.HtmlToPdfObject;
import logic.models.FinalCollectedData;

public class ReportGenerator
{
	private Configuration cfg;
	private String outputPath;
	private FinalCollectedData finalCollectedData;
	private Map<String, Object> input;

	public ReportGenerator(final FinalCollectedData finalCollectedData, final String outputPath)
	{
		this.outputPath = outputPath;
		this.finalCollectedData = finalCollectedData;

		cfg = new Configuration();
		cfg.setClassForTemplateLoading(ReportGenerator.class, "/");
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		input = new HashMap<>();
		populateTemplateInput(input, finalCollectedData);

	}

	private void populateTemplateInput(final Map<String, Object> input,
			final FinalCollectedData finalCollectedData)
	{
		input.put("methodName", finalCollectedData.getMethodName());
		input.put("numberOfParameters", finalCollectedData.getNumberOfParameters());
		input.put("cyclomaticComplexity", finalCollectedData.getCyclomaticComplexity());
		input.put("inputParameterData", finalCollectedData.getInputDataResult().getParametersData());
		input.put("conditionData", finalCollectedData.getConditionData());
		input.put("methodRaw", finalCollectedData.getMethodRaw());
		input.put("numberOfConditionsDependentOnParameters", finalCollectedData.getNumberOfConditionsDependentOnParameters());
		input.put("nrOfLines", finalCollectedData.getNumberOfLines());
		input.put("minTestCases", finalCollectedData.getMinTestCases());
		input.put("maxTestCases", finalCollectedData.getMaxTestCases());
	}

	public File process() throws IOException, TemplateException
	{
		Template template = cfg.getTemplate("index.ftl");

		String fileName = "Analyzed_Report_" + finalCollectedData.getMethodName() + "_" + System.nanoTime();

		String htmlFullPath = String.format("%s/%s.html", outputPath, fileName);
		String pdfFullPath = String.format("%s/%s.pdf", outputPath, fileName);

		try (FileWriter fileWriter = new FileWriter(htmlFullPath))
		{
			template.process(input, fileWriter);
		}

		boolean convert = HtmlToPdf.create()
				.documentTitle(finalCollectedData.getMethodName())
				.object(HtmlToPdfObject.forUrl(new File(htmlFullPath).getPath())
						.enableJavascript(true)
						.blockLocalFileAccess(false)
						.usePrintMediaType(true)
						.javascriptDelay(1000)
						.useExternalLinks(true)

				)
				.disableSmartShrinking(true)
				.convert(pdfFullPath);


		if (convert)
		{
			Files.delete(Paths.get(new File(htmlFullPath).getPath()));
		}


		return new File(pdfFullPath);
	}

}
