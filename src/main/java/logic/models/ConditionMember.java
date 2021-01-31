package logic.models;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.Type;
import lombok.Data;

@Data
public class ConditionMember
{
	private boolean isVariableType;
	private Type type;
	private Expression expression;
}
