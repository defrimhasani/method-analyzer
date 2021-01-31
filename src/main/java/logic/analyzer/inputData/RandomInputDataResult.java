package logic.analyzer.inputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class RandomInputDataResult
{
	private List<ParameterData> parametersData;

	public RandomInputDataResult()
	{
		parametersData = new ArrayList<>();
	}

	public void add(ParameterData parameterData)
	{
		parametersData.add(parameterData);
	}

	@Data
	public static class ParameterData
	{
		private String parameter;
		private List<String> inputExamples = new ArrayList<>();
		private String examples;

		public String getExamples(){
			examples =Arrays.toString(inputExamples.toArray());
			return examples;
		}

		public void addExample(final String example)
		{
			inputExamples.add(example);
		}

	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();

		List<ParameterData> parameters = getParametersData();

		result.append("Random Data Generated for method")
				.append("\n")
				.append("---------------------------------")
				.append("\n");

		parameters.forEach(parametersDatum -> {

			result.append("Parameter : ")
					.append(parametersDatum.getParameter())
					.append("\n");

			if (!parametersDatum.getInputExamples().isEmpty())
			{
				result.append("Examples : ")
						.append("\n");

				parametersDatum
						.getInputExamples()
						.forEach(example -> result.append("* ")
								.append(example)
								.append("\n"));
			}
			else
			{
				result.append("No examples :(")
						.append("\n");
			}
			result.append("\n");
		});

		return result.toString();

	}
}
