package search_app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class CommonSearcher<Solution> implements Searcher<Solution>{
	@SuppressWarnings("rawtypes")
	protected PriorityQueue<State> open;
	private int evaluatedNodes;
	private ArrayList<State> al;
	
	@SuppressWarnings("rawtypes")
	public CommonSearcher() {
		//super();
		Comparator<State> comp = new StateComparator();
		this.open = new PriorityQueue<State>(comp);
		this.setEvaluatedNodes(0);
		this.al = new ArrayList<State>();
	}

	public int getEvaluatedNodes() {
		return evaluatedNodes;
	}

	public void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}
	@SuppressWarnings("rawtypes")
	protected State popOpenList() {
		this.evaluatedNodes++;
		return this.open.poll();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Solution backTrace(State goal, State initial)
	{
		//System.out.println("im here");
		//System.out.println(goal.getCameFrom().getCost());
		//return (Solution) backTrace(goal.getCameFrom(), initial);
		if(goal.equals(initial))
		{
			al.add(initial);
			return (Solution) al;
		}
		else
		{
			al.add(goal);
			backTrace(goal.getCameFrom(), initial);
			return (Solution) al;
			//al.add(goal);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void addToOpenList(State s)
	{
		//s.setCost(s.getCost()+s.getCameFrom().getCost());
		open.add(s);
	}
	
}
