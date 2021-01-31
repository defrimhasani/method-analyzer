package logic.analyzer.dataAnalyzer.conditionAnalyzer.expressionVariableValueProducers;


import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import logic.models.ConditionMember;
import logic.models.IfStatementExpressionVariableValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MethodCallExpressionResultProducer extends AbstractExpressionResultProducer
{
	public IfStatementExpressionVariableValue getResult(final MethodCallExpr e, final MethodDeclaration methodDeclaration, boolean positiveCondition)
	{
		IfStatementExpressionVariableValue ifStatementExpressionVariableValue =
				new IfStatementExpressionVariableValue();

		ConditionMember expressionValue = getExpressionValue(e.getScope().get(), methodDeclaration);

		String attributeNameValueToGenerate = expressionValue.getExpression().asNameExpr().getNameAsString();

		String valueOfAttribute = stringValueProducer.getStringValue(e.getArgument(0).asLiteralStringValueExpr().getValue(), e.getNameAsString(), positiveCondition);

		ifStatementExpressionVariableValue.setVariableName(attributeNameValueToGenerate);
		ifStatementExpressionVariableValue.setVariableValue(valueOfAttribute);


		return ifStatementExpressionVariableValue;
	}

}
