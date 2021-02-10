package logic.analyzer.dataAnalyzer.conditionAnalyzer;

import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import logic.analyzer.AnalyzerTask;

public class AbstractAnalyzer extends AnalyzerTask
{
	public String getVariable(Expression expression)
	{
		if (expression.isBinaryExpr())
		{
			BinaryExpr binaryExpr = expression.asBinaryExpr();

			if (binaryExpr.getLeft().isNameExpr())
			{
				return binaryExpr.getLeft().asNameExpr().getNameAsString();
			}
			if (binaryExpr.getLeft().isArrayAccessExpr())
			{
				ArrayAccessExpr arrayAccessExpr = binaryExpr.getLeft().asArrayAccessExpr();

				return arrayAccessExpr.getName().asNameExpr().getNameAsString();
			}
			if (binaryExpr.getLeft().isFieldAccessExpr())
			{
				FieldAccessExpr fieldAccessExpr = binaryExpr.getLeft().asFieldAccessExpr();
				return fieldAccessExpr.getScope().asNameExpr().getNameAsString();
			}


			if (binaryExpr.getRight().isNameExpr())
			{
				return binaryExpr.getRight().asNameExpr().getNameAsString();
			}
			if (binaryExpr.getRight().isArrayAccessExpr())
			{
				ArrayAccessExpr arrayAccessExpr = binaryExpr.getRight().asArrayAccessExpr();

				return arrayAccessExpr.getName().asNameExpr().getNameAsString();
			}

			if (binaryExpr.getRight().isFieldAccessExpr())
			{
				FieldAccessExpr fieldAccessExpr = binaryExpr.getRight().asFieldAccessExpr();
				return fieldAccessExpr.getScope().asNameExpr().getNameAsString();
			}


		}
		if(expression.isMethodCallExpr()){

			MethodCallExpr methodCallExpr = expression.asMethodCallExpr();

			return methodCallExpr.getScope().get().asNameExpr().getNameAsString();
		}

		return "";
	}

}
