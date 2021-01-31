package logic.analyzer.inputData;

import static logic.analyzer.AnalyzerTask.Severity.INFORMATION;
import static logic.analyzer.AnalyzerTask.Task.GENERATE_DATA_FOR_PARAMETERS;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;
import logic.analyzer.AnalyzerTask;
import logic.analyzer.DataTypeUtils;

public class MethodInputDataGenerator extends AnalyzerTask
{
	private final ArrayDataGenerator arrayDataGenerator;
	private final PrimitiveDataGenerator primitiveDataGenerator;
	private final StringDataGenerator stringDataGenerator;
	private final CustomObjectGenerator customObjectGenerator;

	public MethodInputDataGenerator()
	{
		arrayDataGenerator = new ArrayDataGenerator();
		primitiveDataGenerator = new PrimitiveDataGenerator();
		stringDataGenerator = new StringDataGenerator();
		customObjectGenerator = new CustomObjectGenerator();
	}

	public RandomInputDataResult generateRandomMethodInputData(MethodDeclaration methodDeclaration)
	{
		RandomInputDataResult randomInputDataResult = new RandomInputDataResult();

		final NodeList<Parameter> parameters = methodDeclaration.getParameters();

		parameters.forEach(parameter -> {

			RandomInputDataResult.ParameterData parameterData = new RandomInputDataResult.ParameterData();
			parameterData.setParameter(parameter.toString());

			final Type type = parameter.getType();

			if (type.isArrayType())
			{
				log(GENERATE_DATA_FOR_PARAMETERS, INFORMATION, "Parameter {0} is an array. Generating random data", parameter);
				parameterData.setInputExamples(arrayDataGenerator.getArrayExamples(4, type.asArrayType()));
			}
			else if (type.isPrimitiveType() || DataTypeUtils.isPrimitiveDataType(type))
			{
				log(GENERATE_DATA_FOR_PARAMETERS, INFORMATION, "Parameter {0} is a primitive data type. Generating random data", parameter);
				parameterData.setInputExamples(primitiveDataGenerator
						.getPrimitiveDataExamples(4, type));
			}
			else if (DataTypeUtils.isString(type))
			{
				log(GENERATE_DATA_FOR_PARAMETERS, INFORMATION, "Parameter {0} is a string data type. Generating random data", parameter);

				parameterData.setInputExamples(stringDataGenerator.getStringExamples(4));
			}
			else
			{
				log(GENERATE_DATA_FOR_PARAMETERS, INFORMATION, "Parameter {0} is a custom object type. Generating random data", parameter);
				parameterData.setInputExamples(customObjectGenerator.getCustomObjectExamples(type));
			}

			randomInputDataResult.add(parameterData);
		});

		return randomInputDataResult;
	}
}
