package elder.manhattan;

import elder.network.Node;

public class Station extends Node
{

	private final Block block;
	private int index;
	
	public Station(Block block, int index)
	{
		super(block.x, block.y);
		this.block = block;
		this.index = index;
	}

	public Block getBlock()
	{
		return block;
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
