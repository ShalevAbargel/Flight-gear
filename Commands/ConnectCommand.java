package Commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import expression.ExpressionCalculator;
import interpreter.MyInterpreter;

public class ConnectCommand implements Command{
	
	private static Socket client = null;
	public static  PrintWriter out = null;
	public static boolean stop =false;
		@Override
		public int doCommand(String[] line) {
			if(line.length != 2)
				System.out.println("Error in parameters in dataServer");
			else
			{
				String ip = line[0];
				int port = (int)ExpressionCalculator.calc(line[1]);
				new Thread(()->runClient(ip,port)).start();
			}
			return 0;
		}

		
		private void runClient(String ip, int port) {
			try {
				client = new Socket(ip, port);
				System.out.println("client is connected to simulator server");
				out = new PrintWriter(client.getOutputStream());
				while(!stop)
				{
					out.println("set /controls/engines/engine/throttle 1.0");
                    String command;
					try {
						command = MyInterpreter.commandsQueue.take();
						out.println(command);
						out.flush();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		public static void disconnect() {
			if (client != null)		
				try {
					stop=true;
					out.close();
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
}


