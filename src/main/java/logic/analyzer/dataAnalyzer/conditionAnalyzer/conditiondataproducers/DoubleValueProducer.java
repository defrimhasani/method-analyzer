package logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers;

import java.util.Random;

import com.github.javaparser.ast.expr.BinaryExpr;


public class DoubleValueProducer
{
	public Double getDoubleValue(String currentValue, BinaryExpr e, boolean positiveResult)
	{
		BinaryExpr.Operator operator = e.getOperator();

		if (operator.equals(BinaryExpr.Operator.EQUALS)
				|| operator.equals(BinaryExpr.Operator.GREATER_EQUALS)
				|| operator.equals(BinaryExpr.Operator.LESS_EQUALS))
		{

			if (positiveResult)
			{
				double foundValue = Double.parseDouble(currentValue);

				if (Double.parseDouble(currentValue) == foundValue)
				{
					return foundValue;
				}
			}
			else
			{
				double foundValue = Double.parseDouble(currentValue) * new Random().nextDouble();

				if (Double.parseDouble(currentValue) != foundValue)
				{
					return foundValue;
				}

			}
		}

		if (operator.equals(BinaryExpr.Operator.GREATER) || operator.equals(BinaryExpr.Operator.LESS))
		{
			return getGreaterOrLessResult(currentValue, e, positiveResult);
		}

		if (operator.equals(BinaryExpr.Operator.NOT_EQUALS))
		{

			if (!positiveResult)
			{
				double foundValue = Double.parseDouble(currentValue);

				if (Double.parseDouble(currentValue) == foundValue)
				{
					return foundValue;
				}
			}
			else
			{
				double foundValue = Double.parseDouble(currentValue) * new Random().nextDouble();

				if (Double.parseDouble(currentValue) != foundValue)
				{
					return foundValue;
				}

			}
		}


		return 0.0;
	}

	public Double getGreaterOrLessResult(String currentValue, BinaryExpr e, boolean setPositiveResult)
	{
		double value = Double.parseDouble(currentValue);

		BinaryExpr.Operator operator = e.getOperator();

		if (e.getRight().isDoubleLiteralExpr() && e.getRight().asDoubleLiteralExpr().getValue().equals(currentValue))
		{
			return getResultForGreaterOrLessThanExpression(setPositiveResult, operator, value);
		}

		if (e.getLeft().isDoubleLiteralExpr() && e.getLeft().asDoubleLiteralExpr().getValue().equals(currentValue))
		{
			return getResultForGreaterOrLessThanExpression(setPositiveResult, operator, value);
		}

		return 0.0;

	}

	public double getResultForGreaterOrLessThanExpression(final boolean positiveValue, BinaryExpr.Operator operator, double value)
	{
		if (operator.equals(BinaryExpr.Operator.GREATER))
		{
			return positiveValue ? value + 0.01 : value - 0.01;
		}
		if (operator.equals(BinaryExpr.Operator.LESS))
		{
			return positiveValue ? value - 0.01 : value + 0.01;
		}

		return 0.0;

	}
}
