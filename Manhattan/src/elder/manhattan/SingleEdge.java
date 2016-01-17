package elder.manhattan;

import elder.network.Edge;
import elder.network.Node;

public class SingleEdge extends Edge
{
	
	private SingleEdge reverse;
	
	private int traffic=0;

	public SingleEdge(Node from, Node to)
	{
		super(from,to);
	}

	public SingleEdge getReverse()
	{
		return reverse;
	}

	public void setReverse(SingleEdge reverse)
	{
		this.reverse = reverse;
	}
	
	public String toString()
	{
		return super.toString();
	}

	public void addTraffic(int traffic)
	{
		this.traffic += traffic; 
	}
	
	public int getTraffic()
	{
		return traffic;
	}
	
	public void resetTraffic()
	{
		traffic=0;
	}
	
}
