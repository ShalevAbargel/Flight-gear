package Commands;

import interpreter.MyInterpreter;

public class PrintCommand implements Command{

	@Override
	public int doCommand(String[] line) {
		if(line.length == 2)
		System.out.println(MyInterpreter.symballTable.get(line[1].trim()).getValue());
		else if(line[0].equals("done"))
		{
			System.out.println("done");
			MyInterpreter.commandsTable.get("disconnect").doCommand(line);
		}
		return 0;
	}

}
