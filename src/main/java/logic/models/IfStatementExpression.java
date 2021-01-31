package logic.models;

import lombok.Data;

@Data
public class IfStatementExpression
{
	private String expression;
	private IfStatementExpressionVariableValue positiveValue;
	private IfStatementExpressionVariableValue negativeValue;


}
