package logic.analyzer.inputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.Type;

public class ArrayDataGenerator
{
	public List<String> getArrayExamples(final int nrExamples, final ArrayType arrayType)
	{
		List<String> result = new ArrayList<>();

		result.add("Empty array of type [" + arrayType.getElementType() + "]");

		IntStream.range(1, nrExamples).mapToObj(i -> getArrayData(arrayType)).forEach(result::add);

		return result;

	}

	public String getArrayData(final ArrayType arrayType)
	{
		Type elementType = arrayType.getElementType();

		if (elementType.isPrimitiveType())
		{
			if (elementType.toString().equals("int"))
			{
				int[] randomArray = getRandomArray();

				return randomArray.length > 0 ? Arrays.toString(randomArray) : "";
			}

			// TODO: 11/01/2021 add other types
		}

		return "";
	}

	private int[] getRandomArray()
	{
		Random random = new Random();

		int arraySize = random.nextInt(7);

		int[] result = new int[arraySize];

		for (int i = 0; i < arraySize; i++)
		{
			result[i] = random.nextInt();
		}

		return result;
	}
}
