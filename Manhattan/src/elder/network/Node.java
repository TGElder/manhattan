package elder.network;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

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
	
	public void removeNeighbour(Edge edge)
	{
		assert(edges.contains(edge));
		edges.remove(edge);
	}
	
	public <T extends Edge> void clearNeighbours(Class<T> type)
	{		
		Iterator<Edge> iterator = edges.iterator();
		
		while (iterator.hasNext())
		{
			if (type.isInstance(iterator.next()))
			{
				iterator.remove();
			}
		}

		
	}
	
	public void clearNeighbours()
	{		
		edges.clear();
	}

	
	public Collection<Edge> getEdges()
	{
		return edges;
	}
	
	public <T extends Edge> Collection<T> getEdges(Class<T> type)
	{
		Collection<T> out = new HashSet<T> ();
		
		for (Edge edge : getEdges())
		{
			if (type.isInstance(edge))
			{
				out.add((T)(edge));
			}
		}
		
		return out;
	}
	
	public <T extends Edge> T getEdge(Node node, Class<T> type)
	{
		for (Edge edge : edges)
		{
			if (edge.b==node)
			{
				if (type.isInstance(edge))
				{
					return (T)(edge);
				}
			}
		}
		
		return null;
	}
	
	public <T extends Edge> boolean hasEdge(Node node, Class<T> type)
	{
		return getEdge(node,type)!=null;
	}
	

	

}
