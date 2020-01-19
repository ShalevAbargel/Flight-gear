package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import test.TestSetter;
import view.MainWindowController;

public class Main {

	public static void main(String[] args) {
		int port = 5555;
		TestSetter.runServer(port);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*model.client = new Socket("127.0.0.1", port);
		model.client.setSoTimeout(20000);
		model.out = new PrintWriter(model.client.getOutputStream());
		model.in = new BufferedReader(new InputStreamReader(model.client.getInputStream()));*/

	}

}
