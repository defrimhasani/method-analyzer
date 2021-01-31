package logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers;

import java.util.Random;

import com.github.javaparser.ast.expr.BinaryExpr;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntegerValueProducer
{
	public Integer getIntegerValue(String currentValue, BinaryExpr e, boolean positiveResult)
	{
		log.info("Running Value producer for integer type");

		BinaryExpr.Operator operator = e.getOperator();

		if (operator.equals(BinaryExpr.Operator.EQUALS)
				|| operator.equals(BinaryExpr.Operator.GREATER_EQUALS)
				|| operator.equals(BinaryExpr.Operator.LESS_EQUALS))
		{
			log.info("Extracted Operator = {}. Trying to find a value that is equal to {}", operator, currentValue);

			if (positiveResult)
			{
				int foundValue = Integer.parseInt(currentValue);

				if (Integer.parseInt(currentValue) == foundValue)
				{
					return foundValue;
				}
			}
			else
			{
				int foundValue = Integer.parseInt(currentValue) * new Random().nextInt(5);

				if (Integer.parseInt(currentValue) != foundValue)
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
				int foundValue = Integer.parseInt(currentValue);

				if (Integer.parseInt(currentValue) == foundValue)
				{
					return foundValue;
				}
			}
			else
			{
				int foundValue = Integer.parseInt(currentValue) * new Random().nextInt(5);

				if (Integer.parseInt(currentValue) != foundValue)
				{
					return foundValue;
				}

			}
		}


		return 0;
	}

	public Integer getGreaterOrLessResult(String currentValue, BinaryExpr e, boolean setPositiveResult)
	{
		int value = Integer.parseInt(currentValue);

		BinaryExpr.Operator operator = e.getOperator();


		if (e.getRight().isIntegerLiteralExpr() && e.getRight().asIntegerLiteralExpr().getValue().equals(currentValue) && !e.getLeft().isIntegerLiteralExpr())
		{
			if (operator.equals(BinaryExpr.Operator.GREATER))
			{
				return getResultForGreaterOrLessThanExpression(setPositiveResult, BinaryExpr.Operator.GREATER, value);
			}
			if (operator.equals(BinaryExpr.Operator.LESS))
			{
				return getResultForGreaterOrLessThanExpression(setPositiveResult, BinaryExpr.Operator.LESS, value);
			}

		}

		if (e.getLeft().isIntegerLiteralExpr() && e.getLeft().asIntegerLiteralExpr().getValue().equals(currentValue) && !e.getRight().isIntegerLiteralExpr())
		{
			if (operator.equals(BinaryExpr.Operator.GREATER))
			{
				return getResultForGreaterOrLessThanExpression(setPositiveResult, BinaryExpr.Operator.LESS, value);
			}
			if (operator.equals(BinaryExpr.Operator.LESS))
			{
				return getResultForGreaterOrLessThanExpression(setPositiveResult, BinaryExpr.Operator.GREATER, value);
			}

		}

		return 0;

	}

	public int getResultForGreaterOrLessThanExpression(final boolean positiveValue, BinaryExpr.Operator operator, int value)
	{
		if (operator.equals(BinaryExpr.Operator.GREATER))
		{
			if (positiveValue)
			{
				return value + 1;
			}
			else
			{
				return value - 1;
			}
		}
		if (operator.equals(BinaryExpr.Operator.LESS))
		{
			if (positiveValue)
			{
				return value - 1;
			}
			else
			{
				return value + 1;
			}
		}

		return 0;

	}

}
