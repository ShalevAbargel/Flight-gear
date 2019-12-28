package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class MyTestClientHandler<Problem,Solution> implements ClientHandler {
	private Solver<Problem,Solution> s;
	private CacheManager<Problem,Solution> cm;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient)
	{
		
		Problem line;
		Solution solution;
		String end = "end";
		//this.s = new StringReverser();
		//this.cm  = new FileCacheManager();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter writer =new PrintWriter(new OutputStreamWriter(outToClient));

		try {
			while(!(line =(Problem) in.readLine()).equals(end)) {
				if (cm.existS(line))
					solution = cm.giveSol(line);
		
				else
				{
					solution = s.solve(line);
					cm.save(line, solution);
				}
				writer.println(solution);
				writer.flush();
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.close();
	}


	public MyTestClientHandler(Solver<Problem, Solution> s, CacheManager<Problem, Solution> cm) {
		super();
		this.s =  s;
		this.cm = cm;
	}	
}
