package logic.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AnalyzedMethodResult
{
	private String methodName;
	private List<IfStatement> ifStatements;

	public AnalyzedMethodResult()
	{
		ifStatements = new ArrayList<>();
	}


	@Override
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("Method : ")
				.append(methodName)
				.append("\n")
				.append("\n");

		if (!ifStatements.isEmpty())
		{
			for (IfStatement ifStatement : ifStatements)
			{
				stringBuilder
						.append(ifStatements.indexOf(ifStatement) + 1)
						.append(". ")
						.append("Statement information")
						.append("\n")
						.append("-----------------------")
						.append("\n")
						.append(ifStatement.getStatementValue())
						.append("\n")
						.append("-----------------------")
						.append("\n");

				List<IfStatementExpression> conditions = ifStatement.getConditions();

				if (!conditions.isEmpty())
				{
					for (IfStatementExpression condition : conditions)
					{
						stringBuilder
								.append(ifStatements.indexOf(ifStatement) + 1)
								.append(".")
								.append(conditions.indexOf(condition) + 1)
								.append(" ")
								.append(condition.getExpression())
								.append("\n");

						IfStatementExpressionVariableValue positiveValue = condition.getPositiveValue();

						stringBuilder.append("Expected value for TRUE [")
								.append(positiveValue.getVariableName())
								.append(" = ")
								.append(positiveValue.getVariableValue())
								.append("]")
								.append("\n");

						IfStatementExpressionVariableValue negativeValue = condition.getNegativeValue();

						stringBuilder.append("Expected value for FALSE [")
								.append(negativeValue.getVariableName())
								.append(" = ")
								.append(negativeValue.getVariableValue())
								.append("]")
								.append("\n");

					}

					if (ifStatement.getPositiveValues() != null)
					{

						stringBuilder
								.append("If statement true when : ");


						ifStatement.getPositiveValues()
								.forEach(v -> {
									stringBuilder.append(v.getLeft())
											.append("=")
											.append(v.getRight())
											.append(",");
								});

						stringBuilder.append("\n");

					}


					if (ifStatement.getNegativeValues() != null)
					{

						stringBuilder
								.append("If statement false when : ");

						ifStatement.getNegativeValues()
								.forEach(v -> {
									stringBuilder.append(v.getLeft())
											.append("=")
											.append(v.getRight())
											.append(",");
								});

						stringBuilder.append("\n");

					}

				}

			}

		}

		return stringBuilder.toString();
	}
}
