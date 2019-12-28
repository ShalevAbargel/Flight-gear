package Commands;

import interpreter.MyInterpreter;
import interpreter.Property;

public class VarCommand implements Command{

	@Override
	public int doCommand(String[] line) {//x = bind "fmskmkf" 
		String str = "";
		String[] arr;
		if(line.length == 1)
			MyInterpreter.symballTable.put(line[0], new Property<Double>());
		else {
			for(String s : line)
				str = str + s;
			if(str.contains("=bind"))
			{
				arr = str.split("=bind");
				MyInterpreter.commandsTable.get("bind").doCommand(arr);
			}
			else if(str.contains("="))
			{
				arr = str.split("=");
				MyInterpreter.commandsTable.get("=").doCommand(arr);
			}
		}
		return 0;
	}

}
