package logic.analyzer.dataAnalyzer.conditionAnalyzer.expressionVariableValueProducers;


import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers.ArrayAccessValueProducer;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers.DoubleValueProducer;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers.FieldAccessValueProducer;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers.IntegerValueProducer;
import logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers.StringValueProducer;
import logic.models.ConditionMember;

public class AbstractExpressionResultProducer
{
	protected IntegerValueProducer integerValueProducer;
	protected StringValueProducer stringValueProducer;
	protected DoubleValueProducer doubleValueProducer;
	protected FieldAccessValueProducer fieldAccessValueProducer;
	protected ArrayAccessValueProducer arrayAccessValueProducer;

	public AbstractExpressionResultProducer()
	{
		this.integerValueProducer = new IntegerValueProducer();
		this.stringValueProducer = new StringValueProducer();
		this.doubleValueProducer = new DoubleValueProducer();
		this.fieldAccessValueProducer = new FieldAccessValueProducer();
		this.arrayAccessValueProducer = new ArrayAccessValueProducer();
	}

	public ConditionMember getExpressionValue(final Expression expression, final MethodDeclaration methodDeclaration)
	{
		ConditionMember conditionMember = new ConditionMember();

		if (isVariableCall(expression))
		{
			final Type variableType = getParameterType(expression.toString(), methodDeclaration);

			conditionMember.setExpression(expression);
			conditionMember.setVariableType(true);
			conditionMember.setType(variableType);
		}
		else
		{
			conditionMember.setVariableType(false);
			conditionMember.setExpression(expression);
		}

		return conditionMember;
	}

	public Type getParameterType(final String attributeName, final MethodDeclaration methodDeclaration)
	{

		Parameter parameter = methodDeclaration.getParameters()
				.stream()
				.filter(param -> param.getName().toString().equals(attributeName))
				.findFirst()
				.orElse(null);

		if (parameter == null)
		{
			return getTypeIfInitialized(methodDeclaration, attributeName);
		}
		else
		{

			return parameter.getType();
		}
	}

	private Type getTypeIfInitialized(final MethodDeclaration methodDeclaration, final String attributeName)
	{
		NodeList<Statement> statements = methodDeclaration
				.getBody()
				.get()
				.getStatements();

		for (Statement statement : statements)
		{
			if (statement.isExpressionStmt())
			{
				VariableDeclarationExpr variableDeclarationExpr = statement.asExpressionStmt()
						.getExpression()
						.asVariableDeclarationExpr();

				NodeList<VariableDeclarator> variables = variableDeclarationExpr.getVariables();

				for (VariableDeclarator variable : variables)
				{
					if (variable.getName().toString().equals(attributeName))
					{
						return variable.getType();
					}
				}
			}
		}

		return null;
	}

	private boolean isVariableCall(Expression expression)
	{
		return !expression.isDoubleLiteralExpr() &&
				!expression.isIntegerLiteralExpr() &&
				!expression.isBinaryExpr() &&
				!expression.isStringLiteralExpr();
	}
}
