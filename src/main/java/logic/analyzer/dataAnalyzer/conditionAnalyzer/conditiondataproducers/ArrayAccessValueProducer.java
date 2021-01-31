package logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers;

import com.github.javaparser.ast.expr.BinaryExpr;

public class ArrayAccessValueProducer
{
	public String getValue(final BinaryExpr e, final boolean positiveCondition)
	{
		if (e.getLeft().isArrayAccessExpr() && e.getOperator().equals(BinaryExpr.Operator.LESS) && e.getRight().isIntegerLiteralExpr())
		{
			return positiveCondition ? String.valueOf(Integer.parseInt(e.getRight().asIntegerLiteralExpr().getValue()) - 1) :
					String.valueOf(Integer.parseInt(e.getRight().asIntegerLiteralExpr().getValue()));
		}
		if (e.getLeft().isArrayAccessExpr() && e.getOperator().equals(BinaryExpr.Operator.GREATER) && e.getRight().isIntegerLiteralExpr())
		{

			return positiveCondition ? String.valueOf(Integer.parseInt(e.getRight().asIntegerLiteralExpr().getValue()) + 1) :
					String.valueOf(Integer.parseInt(e.getRight().asIntegerLiteralExpr().getValue()));
		}
		if (e.getLeft().isIntegerLiteralExpr() && e.getOperator().equals(BinaryExpr.Operator.GREATER) && e.getRight().isArrayAccessExpr())
		{

			return positiveCondition ? String.valueOf(Integer.parseInt(e.getLeft().asIntegerLiteralExpr().getValue()) - 1) :
					String.valueOf(Integer.parseInt(e.getLeft().asIntegerLiteralExpr().getValue()));
		}
		if (e.getLeft().isIntegerLiteralExpr() && e.getOperator().equals(BinaryExpr.Operator.LESS) && e.getRight().isArrayAccessExpr())
		{

			return positiveCondition ? String.valueOf(Integer.parseInt(e.getLeft().asIntegerLiteralExpr().getValue()) + 1) :
					String.valueOf(Integer.parseInt(e.getLeft().asIntegerLiteralExpr().getValue()));

		}
		return "";
	}

}
