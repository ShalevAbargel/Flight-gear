package Commands;

import java.util.Set;

import expression.ExpressionCalculator;
import interpreter.MyInterpreter;

public class returnCommand implements Command{

	@Override
	public int doCommand(String[] line) {
		StringBuilder sb = new StringBuilder();
		for(int i =1; i < line.length;i++)
			sb.append(line[i]);
		String exp = sb.toString();
		Set<String> keySet = MyInterpreter.symballTable.keySet();
		for(String s: keySet)
		{
			if(exp.contains(s))
				exp = exp.replace(s, MyInterpreter.symballTable.get(s).getValue().toString());
		}
		double solution = ExpressionCalculator.calc(exp);
		return (int)solution;
	}

}
