package logic.analyzer.dataAnalyzer.conditionAnalyzer.processors;

import static logic.analyzer.AnalyzerTask.Severity.INFORMATION;
import static logic.analyzer.AnalyzerTask.Task.ANALYZE_CONDITIONS;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import logic.analyzer.AnalyzerTask;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.expressionVariableValueProducers.BinaryExpressionResultProducer;
import logic.models.IfStatement;
import logic.models.IfStatementExpression;
import logic.models.IfStatementExpressionVariableValue;

public class ForStatementProcessor extends AnalyzerTask
{
	private final BinaryExpressionResultProducer binaryExpressionResultProducer;

	public ForStatementProcessor()
	{
		this.binaryExpressionResultProducer = new BinaryExpressionResultProducer();
	}

	public void process(ForStmt forStmt, MethodDeclaration methodDeclaration, final List<IfStatement> result)
	{
		IfStatementExpression ifStatementExpression = new IfStatementExpression();

		log(ANALYZE_CONDITIONS, INFORMATION, "Processing 'for' statement {0}", Collections.singletonList(forStmt.getChildNodes().subList(0, 3)));

		NodeList<Expression> initializationExpressions = forStmt.getInitialization();

		Optional<Expression> condition = forStmt.getCompare();

		IfStatement ifStatement = new IfStatement();

		String statementValue = forStmt.getChildNodes().subList(0, 3).toString();

		ifStatement.setStatementValue(statementValue);

		BinaryExpr binaryExpr = condition.get().asBinaryExpr();

		String variableNameOnCondition = "";

		if (binaryExpr.getRight().isNameExpr())
		{
			variableNameOnCondition = condition.get().asBinaryExpr().getRight().asNameExpr().getNameAsString();
		}

		if (binaryExpr.getRight().isFieldAccessExpr())
		{
			variableNameOnCondition = binaryExpr.getRight().asFieldAccessExpr().getScope().asNameExpr().getNameAsString();

		}

		String finalVariableNameOnCondition = variableNameOnCondition;

		Optional<Parameter> parameter = methodDeclaration.getParameters().stream()
				.filter(param -> param.getNameAsString().equals(finalVariableNameOnCondition))
				.findFirst();

		ifStatementExpression.setExpression(condition.get().toString());

		if (parameter.isPresent())
		{
			IntegerLiteralExpr integerLiteralExpr = initializationExpressions
					.getFirst()
					.orElseThrow(() -> new IllegalArgumentException(""))
					.asVariableDeclarationExpr()
					.getVariables()
					.get(0)
					.getInitializer()
					.orElseThrow(() -> new IllegalArgumentException(""))
					.asIntegerLiteralExpr();

			condition.get().asBinaryExpr().setLeft(integerLiteralExpr);
		}

		IfStatementExpressionVariableValue positive =
				binaryExpressionResultProducer.getResult(condition.get().asBinaryExpr(), methodDeclaration, true);
		log(ANALYZE_CONDITIONS, INFORMATION, "Positive result for expression {0} found {1}", condition.get(), positive);
		ifStatementExpression.setPositiveValue(positive);

		IfStatementExpressionVariableValue negative =
				binaryExpressionResultProducer.getResult(condition.get().asBinaryExpr(), methodDeclaration, false);
		log(ANALYZE_CONDITIONS, INFORMATION, "Negative result for expression {0} found {1}", condition.get(), negative);
		ifStatementExpression.setNegativeValue(negative);

		ifStatement.setConditions(Collections.singletonList(ifStatementExpression));

		result.add(ifStatement);
	}
}
