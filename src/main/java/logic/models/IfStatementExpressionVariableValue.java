package logic.models;

import lombok.Data;

@Data
public class IfStatementExpressionVariableValue
{
	private String variableName;
	private String variableValue;

	@Override
	public String toString()
	{
		return variableName + " = " + variableValue;
	}
}
