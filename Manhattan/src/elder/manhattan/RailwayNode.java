package elder.manhattan;

import elder.network.Node;

public class RailwayNode extends Node
{
	
	private int index;
	
	public RailwayNode(double x, double y)
	{
		super(x, y);
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}


	@Override
	public boolean equals(Object obj)
	{
		return this==obj;
	}
	
	
	

}
