package server_side;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;



@SuppressWarnings("rawtypes")
public class FileCacheManager<Problem,Solution> implements CacheManager {
	
	private Map<Problem,Solution> hm = new HashMap<Problem,Solution>();
	@Override
	public Boolean existS(Object problem) {
		return hm.containsKey(problem);
	}

	@Override
	public Object giveSol(Object problem) {
		return hm.get(problem);
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public void save(Object problem, Object solution) {
		hm.put((Problem)problem,(Solution) solution);
		
		Random rand = new Random();

		// Obtain a number between [0 - 49].
		int n = rand.nextInt(1000);
		String fileName = "SaveSolution" + n + ".txt";
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(fileName);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		     int i = 0;
		        Set entries = hm.entrySet();
		        Iterator iterator = entries.iterator();

		        while(iterator.hasNext()){

		            Map.Entry mapping = (Map.Entry)iterator.next();
				    printWriter.printf("The problem is : ");
		            printWriter.println( mapping.getKey().toString()); 
				    printWriter.printf("and the solution is: ");
				    printWriter.println();
		            printWriter.println((String)hm.get(problem));
		            i++;
		        }
		    printWriter.printf("The problem is : %s", problem);
		    printWriter.println();
		    printWriter.printf("and the solution is: ");
		    printWriter.println();
		    printWriter.println((String)solution);
		    printWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	



	
}
