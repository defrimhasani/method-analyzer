package logic.analyzer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import logic.analyzer.complexity.ComplexityAnalyzer;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.ConditionAnalyzer;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.MethodAnalyzer;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.TestCaseCalculator;
import logic.analyzer.inputData.MethodInputDataGenerator;
import logic.models.AnalyzedMethodResult;
import logic.models.ConditionData;
import logic.models.FinalCollectedData;
import logic.parser.MethodParser;
import lombok.Getter;


@Getter
public class Analyzer
{
	private final ComplexityAnalyzer complexityAnalyzer;
	private final MethodParser methodParser;
	private final MethodInputDataGenerator methodInputDataGenerator;
	private final ConditionAnalyzer conditionAnalyzer;
	private final TestCaseCalculator testCaseCalculator;
	private String rawMethodContent;


	public Analyzer()
	{
		complexityAnalyzer = new ComplexityAnalyzer();
		methodParser = new MethodParser();
		methodInputDataGenerator = new MethodInputDataGenerator();
		conditionAnalyzer = new ConditionAnalyzer();
		testCaseCalculator = new TestCaseCalculator();

	}

	public FinalCollectedData getFinalCollectedData(String content, boolean isFile) throws Exception
	{
		if (isFile)
		{
			readMethod(content);
		}
		else
		{
			readMethodFrom(content);
		}

		FinalCollectedData finalCollectedData = new FinalCollectedData();

		MethodDeclaration method = methodParser.getMethod(rawMethodContent);
		finalCollectedData.addLogContent(methodParser.getContent());

		MethodDeclaration copyOfMethod = method.clone();

		MethodAnalyzer methodAnalyzer = new MethodAnalyzer();
		AnalyzedMethodResult analyzedMethodResult = methodAnalyzer.analyzeMethod(method);
		finalCollectedData.addLogContent(methodAnalyzer.getContent());

		finalCollectedData.setMethodRaw(copyOfMethod.toString());
		finalCollectedData.setMethodName(method.getNameAsString());

		finalCollectedData.setCyclomaticComplexity(complexityAnalyzer.findComplexity(method));
		finalCollectedData.addLogContent(complexityAnalyzer.getContent());

		finalCollectedData.setNumberOfParameters(method.getParameters().size());

		finalCollectedData.setInputDataResult(methodInputDataGenerator.generateRandomMethodInputData(method));
		finalCollectedData.addLogContent(methodInputDataGenerator.getContent());

		finalCollectedData.setNumberOfLines(countNumberOfLines(method));
		finalCollectedData.setMinTestCases(finalCollectedData.getCyclomaticComplexity());


		List<ConditionData> conditionData = new ArrayList<>();

		analyzedMethodResult.getIfStatements().forEach(ifStatement -> ifStatement.getConditions().forEach(condition -> {
			ConditionData data = new ConditionData();
			data.setStatement(ifStatement.getStatementValue());
			data.setCondition(condition.getExpression());
			data.setVariable(condition.getPositiveValue().getVariableName());
			data.setPositiveValue(condition.getPositiveValue().getVariableValue());
			data.setNegativeValue(condition.getNegativeValue().getVariableValue());
			conditionData.add(data);
		}));

		finalCollectedData.setNumberOfConditionsDependentOnParameters(conditionAnalyzer.getNumberOfConditions(method));

		finalCollectedData.setConditionData(conditionData);

		finalCollectedData.setMaxTestCases(testCaseCalculator.getNumberOfTestCases(method));
		finalCollectedData.addLogContent(testCaseCalculator.getContent());


		return finalCollectedData;
	}

	private int countNumberOfLines(MethodDeclaration methodDeclaration)
	{
		String[] split = methodDeclaration.toString().split("\n");

		return split.length;
	}

	public void readMethod(String resourcePath) throws URISyntaxException, IOException
	{
		StringBuilder stringBuilder = new StringBuilder();

		List<String> strings = Files.readAllLines(Paths.get(Analyzer.class.getClassLoader().getResource(resourcePath).toURI()));

		strings.forEach(stringBuilder::append);

		rawMethodContent = stringBuilder.toString();
	}

	public MethodDeclaration getMethodDeclaration() throws Exception
	{
		return methodParser.getMethod(rawMethodContent);

	}

	public void readMethodFrom(String rawMethodContent)
	{
		this.rawMethodContent = rawMethodContent;
	}


}
