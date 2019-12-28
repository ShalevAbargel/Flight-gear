package expression;

public class Plus extends BinaryExpression {
   public Plus(Expression left, Expression right) {
	this.left = left;
	this.right= right;
   }

@Override
public double calculate() {
	
	return this.left.calculate()+this.right.calculate();
}
   
}
