package logic.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.analyzer.AnalyzerTask;
import logic.analyzer.inputData.RandomInputDataResult;
import lombok.Data;

@Data
public class FinalCollectedData
{
	private String methodName;
	private int cyclomaticComplexity;
	private int numberOfParameters;
	private int numberOfConditionsDependentOnParameters;
	private String methodRaw;
	private int numberOfLines;
	private int minTestCases;
	private int maxTestCases;
	private Map<AnalyzerTask.Task, ArrayList<String>> logMessages = new HashMap<>();

	public void addLogContent( Map<AnalyzerTask.Task, ArrayList<String>>data)
	{
		this.logMessages.putAll(data);
	}

	private RandomInputDataResult inputDataResult;

	private List<ConditionData> conditionData;


}
