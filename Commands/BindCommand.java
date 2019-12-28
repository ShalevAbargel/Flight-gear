package Commands;

import interpreter.MyInterpreter;
import interpreter.Property;

public class BindCommand implements Command{

	@Override
	public int doCommand(String[] line) {
		if(!MyInterpreter.symballTable.containsKey(line[0]))
			MyInterpreter.symballTable.put(line[0], new Property<Double>());
		if(!MyInterpreter.symballTable.containsKey(line[1]))
			MyInterpreter.symballTable.put(line[1], new Property<Double>());
		MyInterpreter.symballTable.get(line[0]).bind(MyInterpreter.symballTable.get(line[1]));
		return 0;
	}

}
