package elder.manhattan;

import elder.network.Edge;
import elder.network.Node;

public class SingleEdge extends Edge
{
	
	private SingleEdge reverse;
	
	public final static int FOOT=0;
	public final static int CAR=1;
	public final static int BUS=2;
	public final static int RAIL=3;
	
	private int [] traffic = new int[4];

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

	
	public int getTraffic(int type)
	{
		return traffic[type];
	}
	
	public void addTraffic(int type, int traffic)
	{
		this.traffic[type] += traffic;
	}
	
	public void resetTraffic()
	{
		for (int t=0; t<traffic.length; t++)
		{
			traffic[t] = 0;
		}
	}
	
}
