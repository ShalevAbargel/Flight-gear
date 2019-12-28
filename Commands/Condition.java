package Commands;

import interpreter.MyInterpreter;

public class Condition {

	public boolean condition(String condition)
	{
		String[] symball;
		if(condition.contains("<"))
		{
			symball = condition.split("<");
			double val = MyInterpreter.symballTable.get(symball[0]).getValue();
			if(val < Double.parseDouble(symball[1]))
				return true;
		}
		else if(condition.contains(">"))
		{
			symball = condition.split(">");
			double val = MyInterpreter.symballTable.get(symball[0]).getValue();
			if(val > Double.parseDouble(symball[1]))
				return true;
		}
	return false;	
	}
}
