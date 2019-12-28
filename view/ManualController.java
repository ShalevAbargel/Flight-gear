package view;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class ManualController {
	
	private Circle joyStick;
	private Circle circle;
	protected Slider rudder;
	protected Slider throttle;
	protected DoubleProperty aileron;
	protected DoubleProperty elevator;
	private double currentX;
	private double currentY;
	private double range;
	private double radius;
	
	
	public ManualController(Slider rudder, Slider throttle, Circle circle, Circle joyStick) {
		this.rudder = rudder;
		this.throttle = throttle;
		this.circle = circle;
		this.joyStick = joyStick;
		this.aileron = new SimpleDoubleProperty();
		this.elevator = new SimpleDoubleProperty();
		this.currentX = 0;
		this.currentY = 0;
		this.radius = 92;
		this.range = this.circle.getRadius() - this.joyStick.getRadius();
		// TODO Auto-generated constructor stub
	}
	
	
	public void joyStickClick(MouseEvent event) {
		this.currentX = event.getX();
		this.currentY = event.getY();
		this.joyStick.toFront();
	}
	
	public void joyStickMove(MouseEvent event) {
		joyStick.setCenterX(0);
		joyStick.setCenterY(0);
		
		if (event.getX() >= 0)
			joyStick.setCenterX(Math.min(joyStick.getCenterX() + this.range, event.getX()));
		else
			joyStick.setCenterX(Math.max(joyStick.getCenterX() - this.range, event.getX()));
		
		if (event.getY() >= 0)
			joyStick.setCenterY(Math.min(joyStick.getCenterY() + this.range, event.getY()));
		else
			joyStick.setCenterY(Math.max(joyStick.getCenterY() - this.range, event.getY()));
		
		elevator.set(joyStick.getCenterY() / -this.radius);
		aileron.set(joyStick.getCenterX() / this.radius);
	}
	
	public void joyStickRelease(MouseEvent event) {
		this.elevator.set(0);
		this.aileron.set(0);
		this.joyStick.setCenterX(0);
		this.joyStick.setCenterY(0);
	}
}
