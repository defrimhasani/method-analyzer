package logic.analyzer.dataAnalyzer.conditionAnalyzer.expressionVariableValueProducers;


import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import logic.analyzer.DataTypeUtils;
import logic.models.ConditionMember;
import logic.models.IfStatementExpressionVariableValue;


public class BinaryExpressionResultProducer extends AbstractExpressionResultProducer
{
	public IfStatementExpressionVariableValue getResult(final BinaryExpr e, final MethodDeclaration methodDeclaration, boolean positiveCondition)
	{
		IfStatementExpressionVariableValue ifStatementExpressionVariableValue =
				new IfStatementExpressionVariableValue();

		String attributeNameValueToGenerate = "";
		String valueOfAttribute = "";

		ConditionMember left = getExpressionValue(e.getLeft(), methodDeclaration);
		ConditionMember right = getExpressionValue(e.getRight(), methodDeclaration);

		if (left.isVariableType() && !right.isVariableType())
		{
			attributeNameValueToGenerate = left.getExpression().toString();

			if (left.getType() != null && left.getType().isPrimitiveType())
			{
				if (DataTypeUtils.isInteger(left.getType().asPrimitiveType()))
				{
					valueOfAttribute = String.valueOf(integerValueProducer.getIntegerValue(right.getExpression().toString(), e, positiveCondition));
				}

				if (DataTypeUtils.isDouble(left.getType().asPrimitiveType()))
				{
					valueOfAttribute = String.valueOf(doubleValueProducer.getDoubleValue(right.getExpression().toString(), e, positiveCondition));
				}
			}
			if (left.getExpression().isFieldAccessExpr())
			{
				valueOfAttribute = String.valueOf(fieldAccessValueProducer.getValue(right.getExpression(), e, positiveCondition));
			}
			if (left.getExpression().isArrayAccessExpr())
			{
				valueOfAttribute = String.valueOf(arrayAccessValueProducer.getValue(e, positiveCondition));
			}


		}

		if (!left.isVariableType() && right.isVariableType())
		{
			attributeNameValueToGenerate = right.getExpression().toString();

			if (right.getType() != null && right.getType().isPrimitiveType())
			{
				if (DataTypeUtils.isInteger(right.getType().asPrimitiveType()))
				{
					valueOfAttribute = String.valueOf(integerValueProducer.getIntegerValue(left.getExpression().toString(), e, positiveCondition));
				}
				if (DataTypeUtils.isDouble(right.getType().asPrimitiveType()))
				{
					valueOfAttribute = String.valueOf(doubleValueProducer.getDoubleValue(left.getExpression().toString(), e, positiveCondition));
				}
			}
			if (right.getExpression().isFieldAccessExpr())
			{
				valueOfAttribute = String.valueOf(fieldAccessValueProducer.getValue(right.getExpression(), e, positiveCondition));
			}
			if (right.getExpression().isArrayAccessExpr())
			{
				valueOfAttribute = String.valueOf(arrayAccessValueProducer.getValue(e, positiveCondition));
			}

		}

		if (left.isVariableType() && right.isVariableType())
		{
			attributeNameValueToGenerate = left.getExpression().toString();
			valueOfAttribute = right.getExpression().toString();

			if (e.getOperator().equals(BinaryExpr.Operator.GREATER))
			{
				valueOfAttribute = positiveCondition ? valueOfAttribute + " + 1 " : valueOfAttribute + " -1";
			}
			else if (e.getOperator().equals(BinaryExpr.Operator.LESS))
			{
				valueOfAttribute = positiveCondition ? valueOfAttribute + " -1" : valueOfAttribute + " + 1 ";
			}
		}

		ifStatementExpressionVariableValue.setVariableName(attributeNameValueToGenerate);
		ifStatementExpressionVariableValue.setVariableValue(valueOfAttribute);

		return ifStatementExpressionVariableValue;
	}

}
