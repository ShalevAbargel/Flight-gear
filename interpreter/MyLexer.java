package interpreter;

public class MyLexer implements Lexer{

	@Override
	public String[] lexer(String str) {
		String[] lexer = str.split(" ");
		return lexer;
	}

}
