package Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import expression.ExpressionCalculator;
import interpreter.MyInterpreter;
import interpreter.Property;

public class openDataServer implements Command{

	public static boolean stop=false;
	public static ServerSocket server = null;
	private static Socket aClient;
	private static  BufferedReader in;
	private String[] commands = {"airspeed","alt","roll","pitch","heading","aileron","elevator","rudder","breaks","throttle"};
	private String[] comd = {
			"/instrumentation/airspeed-indicator/indicated-speed-kt",
	        "/instrumentation/altimeter/indicated-altitude-ft", 
	        "/instrumentation/altimeter/pressure-alt-ft",
	        "/instrumentation/attitude-indicator/indicated-pitch-deg",
	        "/instrumentation/attitude-indicator/indicated-roll-deg", 
	        "/instrumentation/attitude-indicator/internal-pitch-deg", 
	        "/instrumentation/attitude-indicator/internal-roll-deg", 
	        "/instrumentation/encoder/indicated-altitude-ft", 
	        "/instrumentation/encoder/pressure-alt-ft", 
	        "/instrumentation/gps/indicated-altitude-ft", 
	        "/instrumentation/gps/indicated-ground-speed-kt", 
	        "/instrumentation/gps/indicated-vertical-speed", 
	        "/instrumentation/heading-indicator/indicated-heading-deg", 
	        "/instrumentation/magnetic-compass/indicated-heading-deg",
	        "/instrumentation/slip-skid-ball/indicated-slip-skid", 
	        "/instrumentation/turn-indicator/indicated-turn-rate", 
	        "/instrumentation/vertical-speed-indicator/indicated-speed-fpm", 
	        "/controls/flight/aileron", 
	        "/controls/flight/elevator", 
	        "/controls/flight/rudder", 
	        "/controls/flight/flaps", 
	        "/controls/engines/current-engine/throttle", 
	        "/engines/engine/rpm",
	        "/position/latitude-deg", 
	        "/position/longitude-deg" };
	@Override
	public int doCommand(String[] line) {
		for(int i =0; i< comd.length; i++)
		{
			MyInterpreter.symballTable.put(comd[i],new Property<Double>());

		}
		if(line.length != 2)
			System.out.println("Error in parameters in dataServer");
		else
		{
			int port = (int)ExpressionCalculator.calc(line[0]);
			int readLinesInSec = (int)ExpressionCalculator.calc(line[1]);
			new Thread(()->runServer(port,readLinesInSec)).start();
		}
		return 0;
	}

	
	private void runServer(int port, int readLineInSec) {
		try {
			server = new ServerSocket(port);
			System.out.println("server is open");
			server.setSoTimeout(1000);
			while(!stop) {
				try {
					Socket client = server.accept();
					System.out.println("simulator is connected to my server");
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					while(!stop) {
						String[] line = in.readLine().split(",");
						for(int i =0; i< comd.length; i++)
							MyInterpreter.symballTable.get(comd[i]).setValue(Double.parseDouble(line[i]));
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}catch(SocketTimeoutException e) {
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void closeServer() {
		if(server != null)
		{		
			try {
				stop = true;
				in.close();
				aClient.close();
				server.close();
				System.out.println("server is closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
