package elder.manhattan;

import java.util.List;

import elder.network.Edge;
import elder.network.Node;

public class MultiEdge extends Edge
{

	private final SingleEdge [] edges;
	public final double length;
	
	private MultiEdge reverse;
	
	public MultiEdge(Node a, Node b, SingleEdge[] edges)
	{
		super(a, b);
		this.edges = edges;
		
		double length=0;
		
		for (SingleEdge e : edges)
		{
			length+=e.length;
		}
		
		this.length = length; 

	}
	

	public SingleEdge [] getEdges()
	{
		return edges;
	}
	
	public SingleEdge [] computeReverse()
	{
		SingleEdge[] reverse = new SingleEdge[edges.length];
		
		for (int e=0; e<edges.length; e++)
		{
			reverse[e] = edges[(edges.length - 1) - e];
		}
		
		return reverse;
		
	}



	public MultiEdge getReverse()
	{
		return reverse;
	}



	public void setReverse(MultiEdge reverse)
	{
		this.reverse = reverse;
	}
	
	

}
