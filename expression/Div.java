package expression;

public class Div extends BinaryExpression{
	   public Div(Expression left, Expression right) {
			this.left = left;
			this.right= right;
		   }

		@Override
		public double calculate() {
			
			return this.left.calculate()/this.right.calculate();
}
}
