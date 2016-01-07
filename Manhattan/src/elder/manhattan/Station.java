package elder.manhattan;

import elder.network.Node;

public class Station extends Node
{

	private final Block block;
	private final int index;
	
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

}
