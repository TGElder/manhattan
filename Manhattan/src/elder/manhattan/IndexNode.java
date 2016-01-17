package elder.manhattan;

import java.util.List;

import elder.network.Node;

public abstract class IndexNode extends Node
{
	
	private Integer index;
		
	public IndexNode(double x, double y, Integer integer)
	{
		super(x, y);
		this.setIndex(integer);
	}

	public Integer getIndex()
	{
		return index;
	}

	public void setIndex(Integer index)
	{
		this.index = index;
	}

}
