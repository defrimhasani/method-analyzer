package logic.analyzer.inputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.javaparser.ast.type.Type;
import logic.analyzer.DataTypeUtils;

public class PrimitiveDataGenerator
{
	public List<String> getPrimitiveDataExamples(final int nrExamples, final Type type)
	{
		final List<String> result = new ArrayList<>();

		if (DataTypeUtils.isShort(type))
		{
			result.add(Short.toString((short) 0));
			result.addAll(getShortExamples(nrExamples));
		}
		else if (DataTypeUtils.isInteger(type))
		{
			result.add(Integer.toString(0));
			result.addAll(getIntegerExamples(nrExamples));
		}
		else if (DataTypeUtils.isDouble(type))
		{
			result.add(Double.toString(0));
			result.addAll(getDoubleExamples(nrExamples));
		}
		else if (DataTypeUtils.isLong(type))
		{
			result.add(Long.toString(0));
			result.addAll(getLongExamples(nrExamples));
		}
		else if (DataTypeUtils.isFloat(type))
		{
			result.add(Float.toString(0));
			result.addAll(getFloatExamples(nrExamples));
		}
		else if (DataTypeUtils.isBoolean(type))
		{
			result.addAll(getBooleanExamples(nrExamples));
		}
		else if (DataTypeUtils.isChar(type))
		{
			result.addAll(getCharacterExamples(nrExamples));
		}

		return result;
	}

	private List<String> getShortExamples(final int nrExamples)
	{
		return IntStream.range(1, nrExamples)
				.mapToObj(i -> String.valueOf((short) new Random().nextInt(Short.MAX_VALUE + 1)))
				.collect(Collectors.toList());
	}

	private List<String> getIntegerExamples(final int nrExamples)
	{
		return IntStream.range(1, nrExamples)
				.mapToObj(i -> String.valueOf(new Random().nextInt(10)))
				.collect(Collectors.toList());
	}

	private List<String> getDoubleExamples(final int nrExamples)
	{
		return IntStream.range(1, nrExamples)
				.mapToObj(i -> String.valueOf(new Random().nextDouble()))
				.collect(Collectors.toList());
	}

	private List<String> getLongExamples(final int nrExamples)
	{
		return IntStream.range(1, nrExamples)
				.mapToObj(i -> String.valueOf(new Random().nextLong()))
				.collect(Collectors.toList());
	}

	private List<String> getFloatExamples(final int nrExamples)
	{
		return IntStream.range(1, nrExamples)
				.mapToObj(i -> String.valueOf(new Random().nextFloat()))
				.collect(Collectors.toList());
	}

	private List<String> getBooleanExamples(final int nrExamples)
	{
		return IntStream.range(0, nrExamples)
				.mapToObj(i -> String.valueOf(new Random().nextBoolean()))
				.collect(Collectors.toList());
	}

	private List<String> getCharacterExamples(final int nrExamples)
	{
		return IntStream.range(0, nrExamples)
				.mapToObj(i -> String.valueOf((char) (new Random().nextInt(26) + 'a')))
				.collect(Collectors.toList());
	}
}
