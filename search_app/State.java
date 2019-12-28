package search_app;


public class State<T> {
	private T state;
	private int cost;
	@SuppressWarnings("rawtypes")
	private State cameFrom;
	
	
	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public State(T state,int cost) {
		super();
		this.state = state;
		this.cost=cost;
	}


	@SuppressWarnings("rawtypes")
	public State getCameFrom() {
		return cameFrom;
	}

	@SuppressWarnings("rawtypes")
	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int value) {
		this.cost = value;
	}
	@SuppressWarnings("rawtypes")
	public boolean equals(State s) {
		//System.out.println("im in equals");
		//System.out.println(this.getState());
		return this.getState().equals(s.getState());
	}

	public State() {
		super();
	}
	
}
