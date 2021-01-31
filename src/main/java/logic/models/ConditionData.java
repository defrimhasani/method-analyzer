package logic.models;

import lombok.Data;

@Data
public class ConditionData
{
	private int nrOfLine;
	private String statement;
	private String condition;
	private String variable;
	private String positiveValue;
	private String negativeValue;
}
