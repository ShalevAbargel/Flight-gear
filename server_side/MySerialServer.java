package server_side;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MySerialServer implements Server {
	public static Object lock = new Object();
	private int port;
	private ClientHandler ch;
	private volatile boolean stop;
	
	public MySerialServer(int port) {
		super();
		this.port = port;
		this.stop = false;
	}

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
				synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ch.handleClient(in, out);
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
