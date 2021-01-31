package logic.analyzer.inputData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StringDataGenerator
{
	public List<String> getStringExamples(final int nrExamples)
	{
		List<String> result = new ArrayList<>();
		result.add("");

		IntStream.range(1, nrExamples)
				.mapToObj(i -> getRandomString())
				.forEach(result::add);

		return result;
	}

	public String getRandomString()
	{
		return Long.toHexString(Double.doubleToLongBits(Math.random()));
	}
}
