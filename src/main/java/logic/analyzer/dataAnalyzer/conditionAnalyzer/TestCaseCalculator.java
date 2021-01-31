package logic.analyzer.dataAnalyzer.conditionAnalyzer;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import logic.analyzer.utils.IfStatementConditionUtils;

public class TestCaseCalculator extends AbstractAnalyzer
{
	public int getNumberOfTestCases(final MethodDeclaration methodDeclaration)
	{
		List<Expression> result = new ArrayList<>();
		List<Expression> duplicates = new ArrayList<>();

		NodeList<Parameter> parameters = methodDeclaration.getParameters();

		NodeList<Statement> statements = methodDeclaration.getBody()
				.get()
				.getStatements();


		for (Statement statement : statements)
		{
			processStatement(statement, parameters, result, duplicates);
		}


		return (result.size() * 2) - duplicates.size();
	}

	public void processStatement(Statement statement, NodeList<Parameter> parameters, List<Expression> result, List<Expression> duplicates)
	{
		if (statement.isIfStmt())
		{
			IfStmt ifStmt = statement.asIfStmt();

			processIfStatement(ifStmt, parameters, result, duplicates);

			Statement thenStmt = ifStmt.getThenStmt();

			if (thenStmt != null && thenStmt.isBlockStmt())
			{
				NodeList<Statement> statements = thenStmt.asBlockStmt().getStatements();

				for (Statement innerStatement : statements)
				{
					processStatement(innerStatement, parameters, result, duplicates);
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
					processStatement(innerStatement, parameters, result, duplicates);
				}
			}
		}


	}

	private void processForStatement(ForStmt forStmt, NodeList<Parameter> parameters, List<Expression> result)
	{
		Expression expression = forStmt.getCompare().get();

		if (isExpressionDependentOnAnyOfParameters(expression, parameters))
		{
			result.add(expression);
		}

	}


	private void processIfStatement(IfStmt ifStmt, NodeList<Parameter> parameters, List<Expression> result, List<Expression> duplicates)
	{
		List<Expression> expressionsList = IfStatementConditionUtils.getExpressionsList(ifStmt.getCondition());

		for (Expression expression : expressionsList)
		{
			if (isExpressionDependentOnAnyOfParameters(expression, parameters))
			{
				if (result.contains(expression))
				{
					duplicates.add(expression);
				}

				result.add(expression);
			}
		}

	}

	private boolean isExpressionDependentOnAnyOfParameters(com.github.javaparser.ast.expr.Expression expression, NodeList<Parameter> parameters)
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
