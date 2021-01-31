package logic.analyzer.dataAnalyzer.conditionAnalyzer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import logic.analyzer.utils.IfStatementConditionUtils;

/**
 * Kjo klase perdoret per te llogaritur numrin e kushteve qe jane direkt te varura nga parametrat hyres
 */
public class ConditionAnalyzer extends AbstractAnalyzer
{

	public int getNumberOfConditions(MethodDeclaration methodDeclaration)
	{
		AtomicInteger result = new AtomicInteger(0);

		NodeList<Parameter> parameters = methodDeclaration.getParameters();

		NodeList<Statement> statements = methodDeclaration.getBody()
				.get()
				.getStatements();

		for (Statement statement : statements)
		{
			processStatement(statement, parameters, result);
		}

		return result.get();
	}

	public void processStatement(Statement statement, NodeList<Parameter> parameters, AtomicInteger result)
	{
		if (statement.isIfStmt())
		{
			IfStmt ifStmt = statement.asIfStmt();

			processIfStatement(ifStmt, parameters, result);

			Statement thenStmt = ifStmt.getThenStmt();

			if (thenStmt != null && thenStmt.isBlockStmt())
			{
				NodeList<Statement> statements = thenStmt.asBlockStmt().getStatements();

				for (Statement innerStatement : statements)
				{
					processStatement(innerStatement, parameters, result);
				}
			}

		}

		if (statement.isForStmt())
		{
			ForStmt forStmt = statement.asForStmt();

			processForStatement(forStmt, parameters, result);

			Statement body = forStmt.getBody();

			if (body != null && body.isBlockStmt())
			{
				NodeList<Statement> statements = body.asBlockStmt().getStatements();

				for (Statement innerStatement : statements)
				{
					processStatement(innerStatement, parameters, result);
				}
			}
		}


	}

	private void processForStatement(ForStmt forStmt, NodeList<Parameter> parameters, AtomicInteger result)
	{
		Expression expression = forStmt.getCompare().get();

		if (isExpressionDependentOnAnyOfParameters(expression, parameters))
		{
			result.getAndIncrement();
		}

	}

	private void processIfStatement(IfStmt ifStmt, NodeList<Parameter> parameters, AtomicInteger result)
	{
		List<Expression> expressionsList = IfStatementConditionUtils.getExpressionsList(ifStmt.getCondition());

		for (Expression expression : expressionsList)
		{
			if (isExpressionDependentOnAnyOfParameters(expression, parameters))
			{
				result.getAndIncrement();
			}
		}

	}

	private boolean isExpressionDependentOnAnyOfParameters(Expression expression, NodeList<Parameter> parameters)
	{
		for (Parameter parameter : parameters)
		{
			String parameterName = parameter.getNameAsString();

			String variable = getVariable(expression);

			if (parameterName.equals(variable))
			{
				return true;
			}

		}

		return false;
	}

}
