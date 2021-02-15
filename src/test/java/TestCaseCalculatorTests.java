import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logic.analyzer.Analyzer;
import logic.models.FinalCollectedData;

public class TestCaseCalculatorTests
{
	public static final String METHOD_ONE = "public int test (int x, int y){\n" +
			"if(x<0){\n" +
			"     x++;\n" +
			"}else{\n" +
			"    if(y>0){\n" +
			"        y++;\n" +
			"    }\n" +
			"    x--;\n" +
			"  }\n" +
			"return x+y;\n" +
			"}";


	@Test
	public void verifyMaxNumberOfTestCases() throws Exception
	{
		Analyzer analyzer = new Analyzer();

		FinalCollectedData finalCollectedData = analyzer.getFinalCollectedData(METHOD_ONE, false);

		assertEquals(4, finalCollectedData.getMaxTestCases());
	}
}
