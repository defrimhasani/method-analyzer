package logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;

public class FieldAccessValueProducer
{
	// 0 < input.length
	public String getValue(final Expression expression, final BinaryExpr e, final boolean positiveCondition)
	{
		if (e.getRight().isFieldAccessExpr() && e.getOperator().equals(BinaryExpr.Operator.LESS) && e.getLeft().isIntegerLiteralExpr())
		{
			if (positiveCondition)
			{
				return String.valueOf(Integer.parseInt(e.getLeft().asIntegerLiteralExpr().getValue()) + 10);
			}
			else
			{
				return String.valueOf(Integer.parseInt(e.getLeft().asIntegerLiteralExpr().getValue()) - 1);
			}
		}

		if(e.getLeft().isFieldAccessExpr() && e.getOperator().equals(BinaryExpr.Operator.GREATER) && e.getRight().isIntegerLiteralExpr()){

			if (positiveCondition)
			{
				return String.valueOf(Integer.parseInt(e.getRight().asIntegerLiteralExpr().getValue()) + 10);
			}
			else
			{
				return String.valueOf(Integer.parseInt(e.getRight().asIntegerLiteralExpr().getValue()) - 1);
			}
		}

		return "";
	}
}
