package elder.manhattan;

import elder.network.Edge;

public class Tube extends Edge
{

	public Tube(Block from, Block to)
	{
		super(from,to);
	}

	public Block getFrom()
	{
		return (Block)a;
	}

	public Block getTo()
	{
		return (Block)b;
	}

}
