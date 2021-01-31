package logic.analyzer.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class IfStatementConditionUtils
{
	private IfStatementConditionUtils()
	{
	}


	public static int getNumberOfExpressions(BinaryExpr expr)
	{
		List<BinaryExpr> expressions = new ArrayList<>();

		processBinaryExpression(expr, expressions);

		return expressions.size();
	}

	public static List<Expression> getExpressionsList(Expression b)
	{
		List<Expression> expressions = new ArrayList<>();

		processBinaryExpression(b, expressions);

		return expressions;

	}

	private static void processBinaryExpression(Expression expression, List<Expression> expressions)
	{
		if (expression.isBinaryExpr())
		{
			if (!expression.asBinaryExpr().getOperator().equals(BinaryExpr.Operator.AND) && !expression.asBinaryExpr().getOperator().equals(BinaryExpr.Operator.OR))
			{
				expressions.add(expression);
			}
			else
			{
				Expression left = expression.asBinaryExpr().getLeft();
				Expression right = expression.asBinaryExpr().getRight();

				processBinaryExpression(left, expressions);
				processBinaryExpression(right, expressions);
			}
		}
		else
		{
			expressions.add(expression);
		}

	}

	public static List<BinaryExpr> getExpressions(BinaryExpr expr)
	{
		List<BinaryExpr> expressions = new ArrayList<>();

		processBinaryExpression(expr, expressions);

		return expressions;
	}

	private static void processBinaryExpression(BinaryExpr binaryExpr, List<BinaryExpr> expressions)
	{
		if (!binaryExpr.getOperator().equals(BinaryExpr.Operator.AND) && !binaryExpr.getOperator().equals(BinaryExpr.Operator.OR))
		{
			expressions.add(binaryExpr);
		}
		else
		{
			Expression left = binaryExpr.getLeft();
			Expression right = binaryExpr.getRight();

			processBinaryExpression(left.asBinaryExpr(), expressions);
			processBinaryExpression(right.asBinaryExpr(), expressions);

		}

	}
}
