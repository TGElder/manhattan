package elder.network;

import java.util.Collection;
import java.util.HashSet;

import elder.geometry.Point;

public class Node extends Point
{
	protected transient Collection<Edge> edges = new HashSet<Edge> ();
	
	public Node(double x, double y)
	{
		super(x,y);
	}


	public void addEdge(Edge edge)
	{
		assert(edge.a==this);
		assert(!edges.contains(edge));
		edges.add(edge);
	}
	
	public void removeEdge(Edge edge)
	{
		assert(edges.contains(edge));
		edges.remove(edge);
	}
	
	public void clearNeighbours()
	{		
		edges.clear();
	}

	
	public Collection<Edge> getEdges()
	{
		return edges;
	}
	
	
	public Edge getEdge(Node node)
	{
		for (Edge edge : edges)
		{
			if (edge.b==node)
			{
				
				return edge;
				
			}
		}
		
		return null;
	}
	
	public boolean hasEdge(Node node)
	{
		return getEdge(node)!=null;
	}
	

	

}
