package logic.analyzer.dataAnalyzer.conditionAnalyzer;

import static logic.analyzer.AnalyzerTask.Severity.INFORMATION;
import static logic.analyzer.AnalyzerTask.Task.ANALYZE_CONDITIONS;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;
import logic.models.AnalyzedMethodResult;
import logic.models.IfStatement;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.processors.ForStatementProcessor;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.processors.IfStatementProcessor;


public class MethodAnalyzer extends AbstractAnalyzer
{
	private final IfStatementProcessor ifStatementProcessor;

	private final ForStatementProcessor forStatementProcessor;

	public MethodAnalyzer()
	{
		this.ifStatementProcessor = new IfStatementProcessor();
		this.forStatementProcessor = new ForStatementProcessor();
	}

	public AnalyzedMethodResult analyzeMethod(MethodDeclaration methodDeclaration)
	{
		AnalyzedMethodResult analyzedMethodResult = new AnalyzedMethodResult();

		analyzedMethodResult.setMethodName(methodDeclaration.getNameAsString());

		NodeList<Statement> statements = methodDeclaration.getBody().get().getStatements();

		List<IfStatement> ifStatements = new ArrayList<>();

		for (Statement statement : statements)
		{
			if (couldContainBlock(statement))
			{
				processBlock(statement, methodDeclaration, ifStatements);
			}
		}

		analyzedMethodResult.setIfStatements(ifStatements);

		return analyzedMethodResult;
	}

	private boolean couldContainBlock(Statement statement)
	{
		return statement.isIfStmt() || statement.isForStmt() || statement.isBlockStmt() || statement.isDoStmt();
	}

	private void processBlock(final Statement statement, final MethodDeclaration methodDeclaration, List<IfStatement> result)
	{
		if (statement.isIfStmt())
		{
			IfStmt ifStmt = statement.asIfStmt();

			log(ANALYZE_CONDITIONS, INFORMATION, "Running If statement processor for {0}", ifStmt.getCondition());

			ifStatementProcessor.processIfStatement(ifStmt, result, methodDeclaration);

			log(ifStatementProcessor.getContent());

			ifStatementProcessor.getContent().clear();

			Statement thenStmt = ifStmt.getThenStmt();

			if (thenStmt != null && couldContainBlock(thenStmt))
			{
				NodeList<Statement> statements = thenStmt.asBlockStmt().getStatements();

				for (Statement innerStatement : statements)
				{
					if (couldContainBlock(innerStatement))
					{
						processBlock(innerStatement, methodDeclaration, result);
					}
				}
			}
		}
		if (statement.isForStmt())
		{
			ForStmt forStmt = statement.asForStmt();

			log(ANALYZE_CONDITIONS, INFORMATION, "Running for statement processor for {0}", forStmt);

			forStatementProcessor.process(forStmt, methodDeclaration, result);

			log(forStatementProcessor.getContent());

			forStatementProcessor.getContent().clear();

			if (couldContainBlock(forStmt))
			{

				NodeList<Statement> statements = forStmt.getBody().asBlockStmt().getStatements();

				for (Statement innerStatement : statements)
				{
					if (couldContainBlock(innerStatement))
					{
						processBlock(innerStatement, methodDeclaration, result);
					}
				}
			}
		}

		if (statement.isWhileStmt())
		{
			WhileStmt whileStmt = statement.asWhileStmt();

		}

		if (statement.isDoStmt())
		{
			DoStmt doStmt = statement.asDoStmt();
		}

		if (statement.isSwitchStmt())
		{

		}

	}


}
