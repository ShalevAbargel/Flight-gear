package expression;

public class Number implements Expression{
	private double value;
public Number(double val) {
	this.value=val;
}
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public double calculate() {
		return this.value;
	}
	
}
