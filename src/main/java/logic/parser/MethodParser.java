package logic.parser;

import static logic.analyzer.AnalyzerTask.Severity.INFORMATION;
import static logic.analyzer.AnalyzerTask.Task.*;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.body.MethodDeclaration;
import logic.analyzer.AnalyzerTask;

public class MethodParser extends AnalyzerTask
{
	private final JavaParser javaParser;

	public MethodParser()
	{
		javaParser = new JavaParser();
	}

	public MethodDeclaration getMethod(final String content) throws GenericException
	{
		log(PARSE_CODE, INFORMATION, "Parsing method");

		ParseResult<MethodDeclaration> methodDeclarationParseResult = javaParser.parseMethodDeclaration(content);

		return methodDeclarationParseResult.getResult().orElseThrow(() -> new GenericException("Something went wrong while parsing code"));

	}

}
