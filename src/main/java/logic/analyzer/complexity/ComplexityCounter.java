package logic.analyzer.complexity;

import lombok.Data;

@Data
public class ComplexityCounter
{
	private int value;

	public void increase()
	{
		value++;
	}

	public void decrease()
	{
		value--;
	}

	public void increase(int valueToAdd){
		value += valueToAdd;
	}

	public void reset(){
		value = 0;
	}


}
