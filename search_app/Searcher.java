package search_app;

public interface Searcher<Solution> {
	@SuppressWarnings("rawtypes")
	public Solution search(Searchable s);
}
