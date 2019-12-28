package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.model;
import view.MainWindowController;
import view.MapDisplayer;

public class viewModel extends Observable implements Observer{
	public StringProperty ip;
	public StringProperty port;
	public model model;
	public MainWindowController view;
    public DoubleProperty markX,markY;
    public DoubleProperty startX,startY;
    public StringProperty path;
    public BooleanProperty alert;
	public BooleanProperty isDataServerConnected;
    private static boolean isConnected = false;
    public DoubleProperty rudder;
    public DoubleProperty throttle;
    public DoubleProperty aileron;
    public DoubleProperty elevator;
	
	public viewModel(MainWindowController view) {
		this.view = view;
		this.addObserver(view);
		this.model = new model(this);
		this.ip = new SimpleStringProperty();
		this.port = new SimpleStringProperty();
		this.startX = new SimpleDoubleProperty();
		this.startY = new SimpleDoubleProperty();
		this.markX = new SimpleDoubleProperty();
		this.markY = new SimpleDoubleProperty();
		this.path =new  SimpleStringProperty();
		this.alert = new SimpleBooleanProperty(false);
		this.isDataServerConnected = new SimpleBooleanProperty(false);
		this.throttle = new SimpleDoubleProperty();
		this.rudder = new SimpleDoubleProperty();
		this.aileron = new SimpleDoubleProperty();
		this.elevator = new SimpleDoubleProperty();
		
	}


	public void openServer() {
		this.model.openServer();
	}
	public void connect() {

			this.model.connectToServer(ip.getValue(), Integer.parseInt(port.getValue()));	

	}
	
	public void connectToCalcServer(int[][] arr) {
			this.model.connectToCalcServer(ip.getValue(), Integer.parseInt(port.getValue()),arr,(int)startX.doubleValue(),(int)startY.doubleValue(),(int)markX.doubleValue(),(int)markY.doubleValue());	
	}

	public void autoPilot(String[] lines) {
		this.model.autoPilot(lines);
	}
	
	public void setRudder() {
		this.model.sendCommand("set /controls/flight/rudder "+rudder.get());
	}
	
	public void setThrottle() {
		this.model.sendCommand("set /controls/engines/current-engine/throttle "+throttle.get());
	}
	
	public void setAileron() {
		this.model.sendCommand("set /controls/flight/aileron "+aileron.get());
	}
	
	public void setElevator() {
		this.model.sendCommand("set /controls/engines/current-engine/elevator "+elevator.get());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == model)
		{
			if(arg != null)
			{
				this.path.setValue(arg.toString());
			}
			else 
			{
				this.setChanged();
				this.notifyObservers();
			}
		}
		
	}

}
