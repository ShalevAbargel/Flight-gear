package interpreter;

import java.util.Observable;
import java.util.Observer;

public class Property<V> extends Observable implements Observer{
	V v;
	
	public void setValue(V v1) {
		if(v != v1)
		{
				v =v1;
				this.setChanged();
				this.notifyObservers(v1);
		}
	}

	public void bind(Property<V> p)
	{
		this.addObserver(p);
		p.addObserver(this);
	}
	
	public V getValue()
	{
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		this.setValue((V) arg);
	}

	
}
