package expression;

public class Mul extends BinaryExpression{
	   public Mul(Expression left, Expression right) {
			this.left = left;
			this.right= right;
		   }

		@Override
		public double calculate() {
			
			return this.left.calculate()*this.right.calculate();
}
}
