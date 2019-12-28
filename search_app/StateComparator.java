package search_app;

import java.util.Comparator;

public class StateComparator implements Comparator<State> {

	@SuppressWarnings("rawtypes")
	@Override
	public int compare(State is1, State is2) {
		if(is1.getCost() > is2.getCost())
			return 1;
		else if(is1.getCost() < is2.getCost())
			return -1;
		return 0;
	}

}
