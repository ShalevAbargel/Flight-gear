package search_app;

import java.util.ArrayList;

public interface Searchable<T> {
	public State<T> getInitialState();
	public State<T> getGoalState();
	@SuppressWarnings("rawtypes")
	public ArrayList<State> getAllPossiblities(State s);
	
}
