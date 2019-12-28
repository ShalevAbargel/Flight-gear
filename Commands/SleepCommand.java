package Commands;

import expression.ExpressionCalculator;

public class SleepCommand implements Command{

	@Override
	public int doCommand(String[] line) {
		try {
			if(line.length == 2)
				Thread.sleep((long) ExpressionCalculator.calc(line[1].trim()));
			else
				Thread.sleep((long) ExpressionCalculator.calc(line[0].trim()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
