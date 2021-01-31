package logic.analyzer.dataAnalyzer.conditionAnalyzer.processors;

import static logic.analyzer.AnalyzerTask.Severity.INFORMATION;
import static logic.analyzer.AnalyzerTask.Task.ANALYZE_CONDITIONS;

import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import logic.analyzer.AnalyzerTask;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.expressionVariableValueProducers.BinaryExpressionResultProducer;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.expressionVariableValueProducers.MethodCallExpressionResultProducer;
import logic.analyzer.utils.IfStatementConditionUtils;
import logic.models.IfStatement;
import logic.models.IfStatementExpression;
import logic.models.IfStatementExpressionVariableValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IfStatementProcessor extends AnalyzerTask
{
	private final BinaryExpressionResultProducer binaryExpressionResultProducer;
	private final MethodCallExpressionResultProducer methodCallExpressionResultProducer;

	public IfStatementProcessor()
	{
		binaryExpressionResultProducer = new BinaryExpressionResultProducer();
		methodCallExpressionResultProducer = new MethodCallExpressionResultProducer();
	}


	public void processIfStatement(IfStmt statement, List<IfStatement> result, MethodDeclaration methodDeclaration)
	{
		log(ANALYZE_CONDITIONS, INFORMATION, "Processing if statement {0}", statement.getCondition());

		result.add(getAnalyzedIfStatement(statement, methodDeclaration));

		final Optional<Statement> elseStmt = statement.getElseStmt();

		if (elseStmt.isPresent())
		{
			if (elseStmt.get().isIfStmt())
			{
				processIfStatement(elseStmt.get().asIfStmt(), result, methodDeclaration);
			}
		}

	}

	public IfStatement getAnalyzedIfStatement(final Statement statement, final MethodDeclaration methodDeclaration)
	{
		IfStatement ifStatement = new IfStatement();

		Expression conditionExpression = statement.asIfStmt().getCondition();

		ifStatement.setStatementValue(conditionExpression.toString());

		List<Expression> expressionsList = IfStatementConditionUtils.getExpressionsList(conditionExpression);

		for (Expression expression : expressionsList)
		{
			if (expressionCanHaveVariables(expression))
			{
				ifStatement.getConditions().add(getAnalyzedIfStatementExpression(expression, methodDeclaration));
			}
		}


		return ifStatement;
	}

	private boolean expressionCanHaveVariables(Expression expression)
	{

		return expression.asBinaryExpr().getRight().isNameExpr() || expression.asBinaryExpr().getLeft().isNameExpr() ||
				expression.asBinaryExpr().getRight().isArrayAccessExpr() || expression.asBinaryExpr().getLeft().isArrayAccessExpr();

	}

	public IfStatementExpression getAnalyzedIfStatementExpression(final Expression expression, final MethodDeclaration methodDeclaration)
	{
		log(ANALYZE_CONDITIONS, INFORMATION, "Trying to find values for {0}", expression);

		IfStatementExpression ifStatementExpression = new IfStatementExpression();

		ifStatementExpression.setExpression(expression.toString());

		if (expression.isBinaryExpr())
		{
			IfStatementExpressionVariableValue positiveResult = binaryExpressionResultProducer.getResult(expression.asBinaryExpr(),
					methodDeclaration, true);
			log(ANALYZE_CONDITIONS, INFORMATION, "Positive value for expression {0} has been found {1}", expression, positiveResult);
			ifStatementExpression.setPositiveValue(positiveResult);

			IfStatementExpressionVariableValue negativeResult = binaryExpressionResultProducer.getResult(expression.asBinaryExpr(),
					methodDeclaration, false);
			log(ANALYZE_CONDITIONS, INFORMATION, "Negative value for expression {0} has been found {1}", expression, negativeResult);

			ifStatementExpression.setNegativeValue(negativeResult);
		}
		else if (expression.isMethodCallExpr())
		{
			ifStatementExpression.setPositiveValue(methodCallExpressionResultProducer.getResult(expression.asMethodCallExpr(),
					methodDeclaration, true));

			log.info("Positive scenario for expression [{}] is {}", expression, ifStatementExpression.getPositiveValue());

			ifStatementExpression.setNegativeValue(methodCallExpressionResultProducer.getResult(expression.asMethodCallExpr(),
					methodDeclaration, false));

			log.info("Negative scenario for expression [{}] is {}", expression, ifStatementExpression.getNegativeValue());

		}

		return ifStatementExpression;
	}
}
