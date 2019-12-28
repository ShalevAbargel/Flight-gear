package expression;

public class Minus extends BinaryExpression{
	   public Minus(Expression left, Expression right) {
			this.left = left;
			this.right= right;
		   }

		@Override
		public double calculate() {
			
			return this.left.calculate()-this.right.calculate();
		}
}
