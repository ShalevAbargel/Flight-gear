package Commands;

import interpreter.MyInterpreter;

public class DisconnectCommand implements Command{

	@SuppressWarnings("static-access")
	@Override
	public int doCommand(String[] line) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ConnectCommand cc = (ConnectCommand) MyInterpreter.commandsTable.get("connect");
		openDataServer ods = (openDataServer) MyInterpreter.commandsTable.get("openDataServer");
		while(cc.out == null)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			cc.out.println("bye");
			cc.disconnect();
			if(ods.server != null)
				ods.closeServer();
		return 0;
	}

}
