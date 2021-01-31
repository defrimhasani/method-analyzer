package logic.analyzer;

import java.util.Arrays;

import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import lombok.Getter;

public final class DataTypeUtils
{


	@Getter
	public enum PrimitiveDataType
	{
		BYTE("byte", "Byte"),
		CHAR("char", "Character"),
		SHORT("short", "Short"),
		INTEGER("int", "Integer"),
		LONG("long", "Long"),
		FLOAT("float", "Float"),
		DOUBLE("double", "Double"),
		BOOLEAN("boolean", "Boolean");

		private final String unboxedKeyword;
		private final String boxedObject;

		PrimitiveDataType(String unboxedKeyword, String boxedObject)
		{
			this.unboxedKeyword = unboxedKeyword;
			this.boxedObject = boxedObject;
		}


		public static PrimitiveDataType getType(Type primitiveType)
		{
			return Arrays.stream(PrimitiveDataType.values())
					.filter(value -> value.getBoxedObject().equalsIgnoreCase(primitiveType.asString()) ||
							value.getUnboxedKeyword().equalsIgnoreCase(primitiveType.asString()))
					.findFirst()
					.orElse(null);
		}
	}

	public static boolean isPrimitiveDataType(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType!= null;
	}

	public static boolean isByte(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType != null && dataType.equals(PrimitiveDataType.BYTE);
	}

	public static boolean isChar(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType != null && dataType.equals(PrimitiveDataType.CHAR);
	}

	public static boolean isShort(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType != null && dataType.equals(PrimitiveDataType.SHORT);
	}

	public static boolean isInteger(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType != null && dataType.equals(PrimitiveDataType.INTEGER);
	}

	public static boolean isLong(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType != null && dataType.equals(PrimitiveDataType.LONG);
	}

	public static boolean isFloat(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType != null && dataType.equals(PrimitiveDataType.FLOAT);
	}

	public static boolean isDouble(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType != null && dataType.equals(PrimitiveDataType.DOUBLE);
	}

	public static boolean isBoolean(final Type type)
	{
		PrimitiveDataType dataType = PrimitiveDataType.getType(type);

		return dataType != null && dataType.equals(PrimitiveDataType.BOOLEAN);
	}

	public static boolean isString(final Type type){

		return type.toString().equalsIgnoreCase("String");
	}

}
