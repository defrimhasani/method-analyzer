package logic.analyzer.dataAnalyzer.conditionAnalyzer.conditiondataproducers;

import org.apache.commons.lang3.StringUtils;

public class StringValueProducer
{

	public static final String STARTS_WITH = "startsWith";
	public static final String ENDS_WITH = "endsWith";
	public static final String EQUALS = "equals";


	public String getStringValue(String currentValue, String methodName, boolean positiveResult)
	{
		if (methodName.equalsIgnoreCase(STARTS_WITH))
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
		if (methodName.equalsIgnoreCase(ENDS_WITH))
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
		if (methodName.equalsIgnoreCase(EQUALS))
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
