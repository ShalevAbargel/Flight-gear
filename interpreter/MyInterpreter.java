package interpreter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import Commands.BindCommand;
import Commands.Command;
import Commands.ConnectCommand;
import Commands.DisconnectCommand;
import Commands.EqualCommand;
import Commands.LoopCommand;
import Commands.PrintCommand;
import Commands.SleepCommand;
import Commands.VarCommand;
import Commands.openDataServer;
import Commands.returnCommand;

public class MyInterpreter {
	public static ConcurrentHashMap<String, Command> commandsTable;
	public static ConcurrentHashMap<String, Property<Double>> symballTable;
	public static Object lock = new Object();
	public static BlockingQueue<String> commandsQueue = new LinkedBlockingDeque<String>();
	
	public MyInterpreter() {
		commandsTable = new ConcurrentHashMap<String, Command>();
		symballTable = new ConcurrentHashMap<String, Property<Double>>();
		commandsTable.put("openDataServer", new openDataServer());
		commandsTable.put("connect", new ConnectCommand());
		commandsTable.put("bind", new BindCommand());
		commandsTable.put("=", new EqualCommand());
		commandsTable.put("disconnect", new DisconnectCommand());
		commandsTable.put("return", new returnCommand());
		commandsTable.put("var", new VarCommand());
		commandsTable.put("while", new LoopCommand());
		commandsTable.put("sleep", new SleepCommand());
		commandsTable.put("print", new PrintCommand());
	}
	
	
	public static  int interpret(String[] lines){
		//new MyInterpreter();
		Lexer l = new MyLexer();
		Parser p = new MyParser();
		return p.parse(l, lines);
	}
}
