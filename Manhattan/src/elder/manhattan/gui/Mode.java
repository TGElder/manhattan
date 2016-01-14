package elder.manhattan.gui;

import elder.manhattan.graphics.CityDrawerLayer;

public abstract class Mode extends CityDrawerLayer implements MouseListener
{

	public Mode(String name)
	{
		super(name);
	}
	
	public abstract void reset();


}
