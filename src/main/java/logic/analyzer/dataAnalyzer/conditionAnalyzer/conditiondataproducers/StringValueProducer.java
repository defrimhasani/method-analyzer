package logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers;

import org.apache.commons.lang3.StringUtils;

public class StringValueProducer
{

	public static final String STARTS_WITH = "startsWith";
	public static final String ENDS_WITH = "endsWith";
	public static final String EQUALS = "equals";


	public String getStringValue(String currentValue, String methodName, boolean positiveResult)
	{
		if (methodName.equals(STARTS_WITH))
		{
			if (positiveResult)
			{
				return currentValue + currentValue;
			}
			else
			{
				return StringUtils.reverse(currentValue) + currentValue;
			}
		}
		if (methodName.equals(ENDS_WITH))
		{
			if (positiveResult)
			{
				return currentValue + currentValue;
			}
			else
			{
				return currentValue + StringUtils.reverse(currentValue);
			}
		}
		if (methodName.equals(EQUALS))
		{
			if (positiveResult)
			{
				return currentValue;
			}
			else
			{
				return currentValue + "ing";
			}
		}
		return "";
	}
}
