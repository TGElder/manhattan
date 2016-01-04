package elder.manhattan;

import java.util.Collection;
import java.util.HashSet;

public class Selection<T>
{
	private T selection=null;
	private final Collection<SelectionListener<T>> listeners = new HashSet<SelectionListener<T>> ();
	
	public T getSelection()
	{
		return selection;
	}
	
	public void setSelection(T selection)
	{
		this.selection = selection;
		for (SelectionListener<T> listener : listeners)
		{
			listener.onSelect(selection);
		}
	}
	
	public void addListener(SelectionListener<T> listener)
	{
		listeners.add(listener);
	}

}
