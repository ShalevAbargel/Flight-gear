package search_app;

import java.util.ArrayList;

public class ArraySearchable implements Searchable {
	private int[][] arr;
	private State initial;
	private State goal;
	private IntegerState[][] intStateArray;
	
	@Override
	public State getInitialState() {
		return this.initial;
	}
	public ArraySearchable(int[][] arr, State initial, State goal) {
		super();
		int place=0;
		this.arr = arr;
		this.initial = initial;
		this.goal = goal;
		int row = arr.length;
		int col = arr[0].length;
		intStateArray = new IntegerState[row+2][col+2];
		for(int i=0; i < col+2; i++)
			intStateArray[0][i] = new IntegerState();
		for(int i=1; i < row+2; i++)
			intStateArray[i][0] = new IntegerState();
		for(int i=1; i < col+2; i++)
			intStateArray[row+1][i] = new IntegerState();
		for(int i=1; i < row+1; i++)
			intStateArray[i][col+1] = new IntegerState();
		for(int i =1; i < row+1; i++)
		{
			for(int j=1; j < col+1; j++)
			{
				intStateArray[i][j] = new IntegerState(place,arr[i-1][j-1]);
				intStateArray[i][j].setPlaceInArr(i+","+j);
				place++;
			}
			
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public State getGoalState() {
		return this.goal;
	}


	@Override
	public ArrayList getAllPossiblities(State s) {
		IntegerState up = new IntegerState();
		IntegerState down = new IntegerState();
		IntegerState left = new IntegerState();
		IntegerState right = new IntegerState();
		ArrayList<State> al = new ArrayList<State>();
		int row = this.intStateArray.length;
		int col = this.intStateArray[0].length;
		String str = ((IntegerState) s).getPlaceInArr();
		String[] split = str.split(",");
		int i = Integer.parseInt(split[0]);
		int j = Integer.parseInt(split[1]);
		right.equalTo(s,intStateArray[i][j+1]);

		down.equalTo(s,intStateArray[i+1][j]);

		left.equalTo(s,intStateArray[i][j-1]);

		up.equalTo(s,intStateArray[i-1][j]);

		IntegerState[] temp= {down,up,right,left};
		for(IntegerState state : temp)
		{
			if(state.getCost() >= 0)
				{
						if(state.equals(up))
						{
							state.setStr("up");
							state.setPlaceInArr(i-1 + ","+ j);
						}
						else if(state.equals(down))
						{
							state.setStr("down");
							state.setPlaceInArr((i+1) + ","+ j);
						}
						else if(state.equals(right))
						{
							state.setStr("right");
							state.setPlaceInArr(i + ","+ (j+1));
						}
						else if(state.equals(left))
						{
							state.setStr("left");
							state.setPlaceInArr(i + ","+ (j-1));
						}
						al.add(state);
					}
				}
		return al;
	}

}
