package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import search_app.ArraySearchable;
import search_app.BestFirstSearch;
import search_app.IntegerState;
import search_app.Searchable;
import search_app.Searcher;


public class MyClientHandler implements ClientHandler{

	private Solver s;
	private CacheManager cm;
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter writer =new PrintWriter(new OutputStreamWriter(outToClient));
		String line="";
		String end = "end";
		int row=1;
		String[] split=null;
		StringBuilder sb = new StringBuilder();
		try {
			line=in.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		split = line.split(",");
		for(String str:split)
			sb.append(str+",");
		try {
			while(!(line = in.readLine()).equals(end))
			{
				split = line.split(",");
				for(String str:split)
					sb.append(str+",");
				row++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//////////////////////////////build the matrix///////////////
		//int[][] arr = new int[split.length][row];
		int[][] arr = new int[row][split.length];
		int temp=0;
		line =sb.toString();
		split = line.split(",");
		
		for(int i=0; i < arr.length; i++)
		{
			for(int j=0; j < arr[0].length; j++)
			{
				arr[i][j] = Integer.parseInt(split[temp]);
				temp++;
			}
			
		}
		int j;
		/////////////////////////////////////////////////////////////////
		try {
			line = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		split = line.split(",");
		int a = Integer.parseInt(split[0]);
		int b = Integer.parseInt(split[1]);
		IntegerState initial = new IntegerState(((a+1)*(b+1)-1), arr[a][b]);
		/////
		initial.setPlaceInArr((a+1) + "," + (b+1));
		/////
		try {
			line = in.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		split = line.split(",");
	    a = Integer.parseInt(split[0]);
	    b = Integer.parseInt(split[1]);
		IntegerState goal = new IntegerState(a*arr[0].length+b, arr[a][b]);

		//IntegerState goal = new IntegerState(((a+1)*(b+1)-1), arr[a][b]);
		////////
		goal.setPlaceInArr((a) + "," + (b));
		///////////////
		
		///////////////////////printing the matrix///////////////////
		/*for(int i =0; i < arr.length; i++)
		{
			for(int j=0; j < arr[0].length; j++)
			{
				System.out.print(arr[i][j]+",");
			}
			System.out.println();
		}*/
		/////////////////////////////////////////////////////////////
		String sol="";
		Searcher<ArrayList<IntegerState>> bfs = new BestFirstSearch<ArrayList<IntegerState>>();
		Searchable intSearcher = new ArraySearchable(arr, initial, goal);
	    if(this.cm.existS(intSearcher))
	    {
	    	sol = (String)this.cm.giveSol(intSearcher);
	    }
	    else
	    	{
	    		this.s = new SolverSearcher<>(bfs);
		
	    		ArrayList<IntegerState> al = new ArrayList<IntegerState>();
		
	    		al = (ArrayList<IntegerState>) s.solve(intSearcher);
	    		for(IntegerState is : al)
	    			{
	    				if(is.getStr()!=null) 
	    				{
	    				if(is.getStr().equals("up"))
	    					sol+="Up";	
	    				else if(is.getStr().equals("down"))
	    					sol+="Down";
	    				else if(is.getStr().equals("right"))
	    					sol+="Right";
	    				else if(is.getStr().equals("left"))
	    					sol+="Left";
	    				}
	    				sol+=",";
	    				
	    			}

	    		String[] str = sol.split(",");
	    		sol ="";
	    		for(int i=str.length-1; i >=0 ; i--)
	    		{
	    			if(i!=0)
	    			{
	    				sol+=str[i];
	    				sol+=",";
	    			}
	    			else
	    				sol+=str[i];
	    		}
	    		this.cm.save(intSearcher, sol);
		}
		writer.println(sol);
		writer.flush();
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.close();
	}

	@SuppressWarnings("rawtypes")
	public MyClientHandler(CacheManager cm) {
		super();
		this.cm = cm;
	}

}
