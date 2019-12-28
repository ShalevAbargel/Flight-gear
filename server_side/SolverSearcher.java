package server_side;

import search_app.Searchable;
import search_app.Searcher;

public class SolverSearcher<Problem,Solution> implements Solver<Problem,Solution> {
	@SuppressWarnings("rawtypes")
	Searcher s;
	@SuppressWarnings("rawtypes")
	public SolverSearcher(Searcher s) {
		super();
		this.s = s;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Solution solve(Problem p) {
		//System.out.println("solversearcher");
		return (Solution) s.search((Searchable)p);
	}


}
