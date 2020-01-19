package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import test.TestSetter;
import viewModel.viewModel;

public class MainWindowController implements Initializable, Observer {
	@FXML
	private TextField port;
	@FXML
	private TextArea textArea;
	@FXML
	private TextField ip;
	@FXML
	private Button submit;
	@FXML
	private RadioButton auto;
	@FXML
	private Circle circle;
	@FXML
	private Circle joyStick;
	@FXML
	private Slider rudder;
	@FXML
	private Slider throttle;
	@FXML
	private RadioButton autoPilot;
	@FXML
	private RadioButton manualPilot;
	@FXML
	private ToggleGroup buttons;

	private ManualController manual;
	private viewModel viewModel;
	DoubleProperty markXForCalc, markYForCalc;
	DoubleProperty markX, markY;
	DoubleProperty startX, startY;
	@FXML
	public MapDisplayer mapDisplayer;
	public static boolean connect;
	private static boolean isConnected = false;
	private static boolean loadData = false;
	StringProperty path;
	BooleanProperty alert;
	BooleanProperty isDataServerConnected;
	private boolean isManual;
	private boolean calculatorServerIsConnected = false;
	private boolean serverIsOpen = false;


	public MainWindowController() {
		this.mapDisplayer = new MapDisplayer();
		this.viewModel = new viewModel(this);
		this.startX = new SimpleDoubleProperty();
		startX.setValue(0);
		this.startY = new SimpleDoubleProperty();
		startY.setValue(0);
		this.markXForCalc = new SimpleDoubleProperty();
		this.markYForCalc = new SimpleDoubleProperty();
		this.markX = new SimpleDoubleProperty();
		this.markY = new SimpleDoubleProperty();
		this.path = new SimpleStringProperty();
		this.textArea = new TextArea();
		this.auto = new RadioButton();
		alert = new SimpleBooleanProperty(false);
		this.rudder = new Slider();
		this.throttle = new Slider();
		this.circle = new Circle();
		this.joyStick = new Circle();
		this.isDataServerConnected = new SimpleBooleanProperty(false);
		this.isManual = false;
		this.autoPilot = new RadioButton();
		this.manualPilot = new RadioButton();

	}

	// connect to server
	@FXML
	private void connect() {
		this.connect = true;
		Parent root = null;
		Stage stage = new Stage();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopUp.fxml"));
			root = fxmlLoader.load();
			MainWindowController mwc = fxmlLoader.getController();
			mwc.viewModel = this.viewModel;
			mwc.mapDisplayer = this.mapDisplayer;
			stage.setTitle("Popup");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// connect to calculator server
	public void connectTOCalcServer() {
		if(!this.calculatorServerIsConnected) {
			this.calculatorServerIsConnected = true;
			this.viewModel.markX.bindBidirectional(this.markXForCalc);
			this.viewModel.markY.bindBidirectional(this.markYForCalc);
			startX.bindBidirectional(this.viewModel.startX);
			startY.bindBidirectional(this.viewModel.startY);
			this.connect = false;
			Parent root = null;
			Stage stage = new Stage();
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopUp.fxml"));
				root = fxmlLoader.load();
				MainWindowController mwc = fxmlLoader.getController();
				mwc.viewModel = this.viewModel;
				mwc.mapDisplayer = this.mapDisplayer;
				stage.setTitle("Popup");
				stage.setScene(new Scene(root));
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("You already connected to calculator server");
			alert.setContentText("Please continue");
			alert.showAndWait();
		}
	}

	public void submit() {
		Stage stage = (Stage) submit.getScene().getWindow();
		this.viewModel.isDataServerConnected.bindBidirectional(this.isDataServerConnected);
		this.viewModel.ip.bindBidirectional(ip.textProperty());
		this.viewModel.port.bindBidirectional(port.textProperty());
		if (connect)
		{
			stage.close();
			this.viewModel.connect();
		}
		else {
			isConnected = true;
			this.viewModel.path.bindBidirectional(this.path);
			stage.close();
			this.viewModel.connectToCalcServer(this.mapDisplayer.mapData);
		}
	}


	// load csv file to canvas
	public void loadData() throws IOException {
		loadData = true;
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
		fileChooser.setInitialDirectory(new File("./"));
		String[] str = {};
		ArrayList<String[]> map = new ArrayList<String[]>();
		File f = fileChooser.showOpenDialog(null);
		if (f != null) {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null)
				map.add(line.split(","));
			int[][] mapData = new int[map.size()][];
			for (int i = 0; i < map.size(); i++) {
				mapData[i] = new int[map.get(i).length];
				for (int j = 0; j < map.get(i).length; j++) {
					mapData[i][j] = Integer.parseInt(map.get(i)[j]);
				}
			}
			this.mapDisplayer.setMapData(mapData);
			this.drawAirplane("start");
		} else
			System.out.println("error with openning csv file");
	}

	// func uses to draw the destination point in map
	public void drawMark() {
		if (loadData) {
			double H = this.mapDisplayer.getHeight();
			double W = this.mapDisplayer.getWidth();
			double h = H / mapDisplayer.mapData.length;
			double w = W / mapDisplayer.mapData[0].length;
			GraphicsContext gc = mapDisplayer.getGraphicsContext2D();
			Image image;
			try {
				image = new Image(new FileInputStream("resources/mark.png"));
				gc.drawImage(image, markX.doubleValue() - w * 2, markY.doubleValue() - h * 2, w * 5, h * 5);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (isConnected) {
				this.viewModel.path.bindBidirectional(this.path);
				viewModel.calcPath(mapDisplayer.mapData);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.drawPath();
			}
			
		}
	}

	void drawAirplane(String curse) {
		if (loadData) {
			double H = this.mapDisplayer.getHeight();
			double W = this.mapDisplayer.getWidth();
			double h = H / mapDisplayer.mapData.length;
			double w = W / mapDisplayer.mapData[0].length;
			GraphicsContext gc = mapDisplayer.getGraphicsContext2D();
			Image image;
			try {
				if (curse.equals("down")) {
					image = new Image(new FileInputStream("resources/plane180.png"));
					gc.drawImage(image, startX.doubleValue() - w, startY.doubleValue() - h, w * 20, h * 10);
				} else if (curse.equals("right")) {
					image = new Image(new FileInputStream("resources/plane90.png"));
					gc.drawImage(image, startX.doubleValue() - w, startY.doubleValue() - h, w * 20, h * 10);
				} else if (curse.equals("left")) {
					image = new Image(new FileInputStream("resources/plane270.png"));
					gc.drawImage(image, startX.doubleValue() - w, startY.doubleValue() - h, w * 20, h * 10);
				} else if (curse.equals("up")) {
					image = new Image(new FileInputStream("resources/plane0.png"));
					gc.drawImage(image, startX.doubleValue() - w, startY.doubleValue() - h, w * 20, h * 10);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// func uses to draw the path from initial point to destination
	public void drawPath() {
		double H = mapDisplayer.getHeight();
		double W = mapDisplayer.getWidth();
		double h = H / mapDisplayer.mapData.length;
		double w = W / mapDisplayer.mapData[0].length;
		double x = 0, y = 0;
		String[] arr = path.getValue().toString().split(",");
		GraphicsContext gc = mapDisplayer.getGraphicsContext2D();
		int down = 0, up = 0, right = 0, left = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].contains("Down")) {
				if (i == 0)
					drawAirplane("down");
				gc.setStroke(Color.BLUE);
				gc.strokeLine(x, y, x, y + h);
				y += h;
				down++;
			} else if (arr[i].contains("Up")) {
				if (i == 0)
					drawAirplane("up");
				gc.setStroke(Color.BLUE);
				gc.strokeLine(x, y, x, y - h);
				y -= h;
				up++;
			} else if (arr[i].contains("Right")) {
				if (i == 0)
					drawAirplane("right");
				gc.setStroke(Color.BLUE);
				gc.strokeLine(x, y, x + w, y);
				x += w;
				right++;
			} else if (arr[i].contains("Left")) {
				if (i == 0)
					drawAirplane("left");
				gc.setStroke(Color.BLUE);
				gc.strokeLine(x, y, x - w, y);
				x -= w;
				left++;
			}

		}
	}

	// load text to text area- loading the script for autopilot
	public void loadText() {
		textArea.clear();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files", "*.txt"));
		fileChooser.setInitialDirectory(new File("./resources"));
		File f = fileChooser.showOpenDialog(null);
		if (f != null) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(f));
				String st;
				try {
					while ((st = br.readLine()) != null) {
						textArea.appendText(st);
						textArea.appendText("\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void openServer() {
		if(!this.serverIsOpen)
		{
			this.serverIsOpen = true;
			this.viewModel.openServer();	
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("You already connected to server");
			alert.setContentText("Please continue");
			alert.showAndWait();
		}
	}

	private void AutoPilot() {
		this.viewModel.autoPilot(textArea.getText().split("\n"));
	}

	public void ChoosePilot() {
		this.buttons = new ToggleGroup();
		manualPilot.setToggleGroup(buttons);
		autoPilot.setToggleGroup(buttons);
		if (buttons.getSelectedToggle().equals(manualPilot) && this.isManual == false) {
			this.isManual = true;
			this.throttle.setDisable(false);
			this.rudder.setDisable(false);
			this.joyStick.setFill(Color.DODGERBLUE);
			this.circle.setFill(Color.DODGERBLUE);
			System.out.println("Switching to manual pilot.");
			this.viewModel.autoPilot(null);
		} else if (buttons.getSelectedToggle().equals(autoPilot)) {
			this.throttle.setDisable(true);
			this.rudder.setDisable(true);
			this.joyStick.setFill(Color.GREY);
			this.circle.setFill(Color.GREY);
			this.isManual = false;
			System.out.println("Switching to auto pilot.");
			this.AutoPilot();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.alert.bindBidirectional(this.viewModel.alert);
		this.isDataServerConnected.bindBidirectional(this.viewModel.isDataServerConnected);
		if (o == this.viewModel) {
			if (this.alert.getValue()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error in port");
				alert.setContentText("Please enter port 5402");
				alert.showAndWait();
				this.alert.setValue(false);
			} else if (this.isDataServerConnected.getValue()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Can't connect to server");
				alert.setContentText("Please open data server before opening client");
				alert.showAndWait();
				this.isDataServerConnected.setValue(false);
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.throttle.setDisable(true);
		this.rudder.setDisable(true);
		this.joyStick.setFill(Color.GREY);
		this.circle.setFill(Color.GREY);
		this.isManual = false;
		mapDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> mapDisplayer.requestFocus());
		mapDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, mark);
		this.manual = new ManualController(rudder, throttle, circle, joyStick);
		this.viewModel.throttle.bind(this.manual.throttle.valueProperty());
		this.viewModel.rudder.bind(this.manual.rudder.valueProperty());
		this.viewModel.aileron.bind(this.manual.aileron);
		this.viewModel.elevator.bind(this.manual.elevator);
		this.throttle.setMin(0);
		this.throttle.setMax(1);
		this.rudder.setMin(-1);
		this.rudder.setMax(1);
		this.manual.rudder.valueProperty().addListener((o, ov, nv) -> {
			if (this.isManual)
				this.viewModel.setRudder();
		});
		this.manual.throttle.valueProperty().addListener((o, ov, nv) -> {
			if (this.isManual)
				this.viewModel.setThrottle();
		});
		this.manual.aileron.addListener((o, ov, nv) -> this.viewModel.setAileron());
		this.manual.elevator.addListener((o, ov, nv) -> this.viewModel.setElevator());

	}

	// event handler used to get value of x and y in map
	EventHandler<MouseEvent> mark = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			markX.setValue(event.getX());
			markY.setValue(event.getY());
			try {
				markYForCalc.setValue(MapDisplayer.mapData.length / mapDisplayer.getHeight() * markY.getValue());
				markXForCalc.setValue(MapDisplayer.mapData[0].length / mapDisplayer.getWidth() * markX.getValue());
			} catch (Exception e) {
				//do nothing, the map is not loaded
			}
			mapDisplayer.redraw();
			drawMark();
		}
	};

	public void joyStickClick(MouseEvent event) {
		if (this.isManual)
			this.manual.joyStickClick(event);
	}

	public void JoyStickMove(MouseEvent event) {
		if (this.isManual) {
			this.manual.joyStickMove(event);
			this.viewModel.setAileron();
			this.viewModel.setAileron();
		}
	}

	public void joyStickRelease(MouseEvent event) {
		if (this.isManual)
			this.manual.joyStickRelease(event);
	}

}
