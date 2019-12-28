package Commands;

import interpreter.MyInterpreter;

public class LoopCommand implements Command{

	@Override
	public int doCommand(String[] lines) {
		Condition c = new Condition();
		String[] line = lines[0].split(" ");
		int i =1;
		String cond ="";
		while(!line[i].equals("{"))
		{
			cond += line[i];
			i++;
		}
		while(c.condition(cond))
		{
			for(int j=1;j<lines.length-1;j++)
				for(String s : MyInterpreter.commandsTable.keySet())
					if(lines[j].contains(s))
						MyInterpreter.commandsTable.get(s).doCommand(lines[j].split(s));
		}
		return 0;
	}

}
