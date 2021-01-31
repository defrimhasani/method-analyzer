package logic.analyzer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class AnalyzerTask
{
	Map<Task, ArrayList<String>> content = new HashMap<>();

	public AnalyzerTask()
	{

	}

	@Getter
	public enum Task
	{
		PARSE_CODE("Task that parses code"),
		CALCULATE_CYCLOMATIC_COMPLEXITY("Task that calculates cyclomatic complexity"),
		GENERATE_DATA_FOR_PARAMETERS("Task that generates data for parameters"),
		CALCULATE_TEST_CASES("Task that calculates number of test cases"),
		ANALYZE_CONDITIONS("Task that analyzes conditions");

		private final String content;

		Task(String content)
		{
			this.content = content;
		}
	}

	public enum Severity
	{
		INFORMATION, WARNING, ERROR
	}

	public void log(Task task, Severity severity, String message, Object... arguments)
	{
		String logMessage = MessageFormat.format("{0} {1} : {2}", LocalDateTime.now(), severity, MessageFormat.format(message, arguments));

		ArrayList<String> strings = content.get(task);

		if (strings == null)
		{
			content.put(task, new ArrayList<>());

			strings = content.get(task);

		}
		strings.add(logMessage);

		content.put(task, strings);
	}

	public void log(Map<Task, ArrayList<String>> data)
	{
		data.forEach((key, value) -> content.get(key).addAll(value));
	}

	public Map<Task, ArrayList<String>> getContent()
	{
		return content;
	}
}
