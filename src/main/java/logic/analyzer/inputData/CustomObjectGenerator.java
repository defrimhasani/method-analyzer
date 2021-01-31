package logic.analyzer.inputData;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.type.Type;

public class CustomObjectGenerator
{
	public List<String> getCustomObjectExamples(final Type type)
	{
		List<String> result = new ArrayList<>();

		result.add("null");

		result.add("new " + type + "()");

		return result;
	}
}
