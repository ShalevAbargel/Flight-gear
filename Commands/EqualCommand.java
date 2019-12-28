package Commands;

import java.util.Set;

import expression.ExpressionCalculator;
import interpreter.MyInterpreter;
import interpreter.Property;

public class EqualCommand implements Command{

	@Override
	public int doCommand(String[] line) {
		if(!MyInterpreter.symballTable.containsKey(line[0].trim()))
			MyInterpreter.symballTable.put(line[0].trim(), new Property<Double>());
		Set<String> keySet = MyInterpreter.symballTable.keySet();
		for(String s: keySet)
		{
			if(line[1].contains(s))
			{
				if(MyInterpreter.symballTable.get(s).getValue().toString().contains("E"))
				{
					String[] str = MyInterpreter.symballTable.get(s).getValue().toString().split("E-");
					Integer x = (int)Double.parseDouble(str[0]);
					Integer pow = (int)Double.parseDouble(str[1]);
					StringBuilder sb = new StringBuilder();
					sb.append("0.");
					for(int i=0;i<pow-1;i++)
						sb.append("0");
					sb.append(x.toString());
					line[1] = line[1].replace(s,sb.toString());
				}

				else {
					line[1] = line[1].replace(s, MyInterpreter.symballTable.get(s).getValue().toString());
					if(line[1].startsWith(" - -"))
						line[1] = line[1].substring(4);
				}
			}
		}
		
		Double val;
		if(line[1].startsWith(" -"))
		{
			line[1] = line[1].substring(2);
			val = (-1)*(ExpressionCalculator.calc(line[1]));
			MyInterpreter.symballTable.get(line[0].trim()).setValue(val);
		}
		else {
			val = ExpressionCalculator.calc(line[1]);
			MyInterpreter.symballTable.get(line[0].trim()).setValue(val);
		}
		try {
		if(line[0].trim().equals("rudder"))
				MyInterpreter.commandsQueue.put("set /controls/flight/rudder " + val);
		else if(line[0].trim().equals("aileron"))
			MyInterpreter.commandsQueue.put("set /controls/flight/aileron " + val);
		else if(line[0].trim().equals("elevator"))
			MyInterpreter.commandsQueue.put("set /controls/flight/elevator " + val);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
		return 0;
	}

}
