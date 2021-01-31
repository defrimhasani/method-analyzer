package logic.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Data;

@Data
public class IfStatement
{
	private String statementValue;

	private List<IfStatementExpression> conditions;
	private List<Pair<String, String>> positiveValues;
	private List<Pair<String, String>> negativeValues;

	public IfStatement()
	{
		conditions = new ArrayList<>();
	}


}
