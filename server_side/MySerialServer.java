package server_side;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {
	
	public MySerialServer(int port) {
		super();
		this.port = port;
		this.stop = false;
	}

	private int port;
	private ClientHandler ch;
	private volatile boolean stop;

	private void runServer(){
		ServerSocket server;
		Socket aClient;
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(3000);
			aClient = server.accept();
			InputStream in = aClient.getInputStream();
			OutputStream out = aClient.getOutputStream();
			while(!this.stop){
					ch.handleClient(in, out);
					in.close();
					out.close();
					aClient.close();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			server.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
}
	
	
	@Override
	public void start (ClientHandler ch){
		this.ch = ch;
		new Thread(()->{
			try {
				runServer();
			} catch (Exception e) {}
		}).start();
	}

	@Override
	public void stop() {
		stop = true;	
	}

}
