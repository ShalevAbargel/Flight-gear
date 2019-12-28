package search_app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BestFirstSearch<Solution> extends CommonSearcher<Solution>{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Solution search(Searchable s) {
		State n;
		open.add(s.getInitialState());
		Set<State> closed = new HashSet<State>();
		while(!open.isEmpty())
		{
			n=this.popOpenList();
			closed.add(n);
			if(n.equals(s.getGoalState()))
				return this.backTrace(n,s.getInitialState());
			ArrayList<State> al = s.getAllPossiblities(n);
			for(State state : al)
			{
				if(!(open.contains(state)&& closed.contains(state)))
						{
							state.setCameFrom(n);	
							open.add(state);
						}
			}
			
		}
		return null;
	}

}
