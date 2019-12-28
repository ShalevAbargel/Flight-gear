package search_app;

public class IntegerState extends State<Integer> {
	private String placeInArr;
	private String str;
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public IntegerState(int place, int cost) {
		super(place, cost);
		this.setCameFrom(null);
		this.setCost(cost);
		this.setState(place);
		this.str=null;
		// TODO Auto-generated constructor stub
	}
	public IntegerState() {
		super();
		this.setCost(-1);
		this.setCameFrom(null);
		this.setState(null);
	}
	
	public String getPlaceInArr() {
		return placeInArr;
	}
	public void setPlaceInArr(String placeInArr) {
		this.placeInArr = placeInArr;
	}
	
	public IntegerState equalTo(State s,IntegerState is)
	{
		if(s.getCameFrom() == is)
		{
			return this;	
		}
		else if(is.getCost()>= 0 && is.getCameFrom() == null)
		{
			is.setCost(is.getCost()+s.getCost());
			is.setCameFrom(s);
			this.setState(is.getState());
			this.setCost(is.getCost());
			this.setCameFrom(is.getCameFrom());
			return this;	
		}
		else if(is.getCost() >=0 && is.getCameFrom() != null)
		{
			if(is.getCost() - is.getCameFrom().getCost() + s.getCost() < is.getCost())
			{
				is.setCost(is.getCost() - is.getCameFrom().getCost()+s.getCost());
				is.setCameFrom(s);
				this.setState(is.getState());
				this.setCost(is.getCost());
				this.setCameFrom(is.getCameFrom());
				return this;	
			}
		}
		return this;
		
	}
}
