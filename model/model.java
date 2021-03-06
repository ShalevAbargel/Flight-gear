package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import interpreter.MyInterpreter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import test.TestSetter;
import viewModel.viewModel;

public class model extends Observable {
	public StringProperty path;
	viewModel vm;
	public static Socket client;
	public static PrintWriter out;
	public static BufferedReader in;
	private MyInterpreter interpreter;
	public BooleanProperty alert;
	public BooleanProperty isDataServerConnected;
	private boolean dataServerConnected = false;
	private Thread autoPilotThread;
	private boolean isAutoPilotOn;
	private boolean isAutoPilotNow;
	public BlockingQueue<pathCalculator> bq;

	public model(viewModel vm) {
		this.vm = vm;
		this.path = new SimpleStringProperty();
		this.alert = new SimpleBooleanProperty(false);
		this.isDataServerConnected = new SimpleBooleanProperty(false);
		this.addObserver(vm);
		this.isAutoPilotOn = false;
		this.isAutoPilotNow = false;
		this.bq = new LinkedBlockingQueue<pathCalculator>();
	}

	// open data server
	public void openServer() {
		String[] line = { "openDataServer 5400 10" };
		dataServerConnected = true;
		interpreter = new MyInterpreter();
		interpreter.interpret(line);
	}


	public void sendCommand(String command) {
		interpreter.commandsQueue.add(command);
	}

	// connect to flightgeer server
	public void connectToServer(String ip, int port) {
		if (port != 5402) {
			this.alert.bindBidirectional(this.vm.alert);
			this.alert.setValue(true);
			this.setChanged();
			this.notifyObservers();
		} else if (!dataServerConnected) {
			this.isDataServerConnected.bindBidirectional(this.vm.isDataServerConnected);
			this.isDataServerConnected.setValue(true);
			this.setChanged();
			this.notifyObservers();
		} else {
			String[] line = { "connect " + ip + " " + port };
			interpreter.interpret(line);
		}
	}

	// connect to path calculator server
	public void connectToCalcServer(String ip, int port) {
		try {
			TestSetter.runServer(port);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			client = new Socket(ip, port);
			client.setSoTimeout(20000);
			out = new PrintWriter(client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Calc sever is not open");
		}
	}
	
	// calculate the path to destination
	public void pathCalc() throws IOException {
		while(true)
		{
			pathCalculator pc;
			try {
				pc = this.bq.take();
				String sol = pc.calc(this,out, in);
				path.setValue(sol);
				this.setChanged();
				this.notifyObservers(path);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	// start autopilot
	public void autoPilot(String[] lines) {
		if (!this.isAutoPilotNow && lines != null)
			this.startAutoPilot(lines);
		else if(this.isAutoPilotOn)
			this.stopAutoPilot();
	}

	@SuppressWarnings("deprecation")
	private void startAutoPilot(String[] lines) {
		if (!this.isAutoPilotOn) {
			this.autoPilotThread = new Thread(() -> interpreter.interpret(lines));
			this.autoPilotThread.start();
			this.isAutoPilotOn = true;
			System.out.println("Auto pilot is ON");
		}
	}

	@SuppressWarnings("deprecation")
	public void stopAutoPilot() {
		if(this.isAutoPilotOn) {
			this.isAutoPilotOn = false;
			this.autoPilotThread.stop();
		}
	}
}