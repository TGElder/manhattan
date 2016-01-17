package elder.manhattan;

import java.util.List;

import elder.network.Node;

public class Highway extends TimeEdge
{
	private final double time;

	public Highway(Node a, Node b, SingleEdge [] edges, double speed)
	{
		super(a, b, edges);
		this.time = length/speed;
	}


	@Override
	public double getTime()
	{
		return time;
	}

}
