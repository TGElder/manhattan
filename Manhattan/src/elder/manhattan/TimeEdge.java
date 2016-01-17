package elder.manhattan;


import elder.network.Node;

public abstract class TimeEdge extends MultiEdge
{
	
	public TimeEdge(Node a, Node b, SingleEdge [] edges)
	{
		super(a, b, edges);
	}
	
	public abstract double getTime();
	

}
