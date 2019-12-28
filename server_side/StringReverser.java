package server_side;

public class StringReverser implements Solver<String, String>{

	@Override
	public String solve(String problem) {
		StringBuilder sb = new StringBuilder(problem);
		return sb.reverse().toString();
	}

}
