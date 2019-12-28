package server_side;

public interface CacheManager <Problem,Solution>{
	public Boolean existS(Problem problem);
	public Solution giveSol(Problem problem);
	public void save(Problem problem, Solution solution);
}
