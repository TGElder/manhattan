package elder.manhattan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import elder.network.Edge;

public class Pathfinder
{

	private final City city;
	private boolean trafficBias = false;
	
	public Pathfinder(City city)
	{
		this.city = city;
	}
	
	public void setTrafficBias(boolean trafficBias)
	{
		this.trafficBias = trafficBias;
	}
	
	public List<SingleEdge> findPath (IndexNode from, IndexNode to, int max)
	{
			
		if (from==to)
		{
			return null;
		}
				
		boolean [] open = new boolean[max];
		boolean [] closed = new boolean[max];
		
		final SingleEdge [] directions = new SingleEdge[max];
		final double [] distances = new double[max];
		
		PriorityQueue<IndexNode> openList = new PriorityQueue<IndexNode> (new Comparator<IndexNode>() 
		{

			@Override
			public int compare(IndexNode a, IndexNode b)
			{
				if (distances[a.getIndex()]<distances[b.getIndex()])
				{
					return -1;
				}
				else if (distances[a.getIndex()]>distances[b.getIndex()])
				{
					return 1;
				}
				else
				{
					return 0;
				}
					
			}
		});
		
		IndexNode node = from;
		int b=node.getIndex();		
		
		init(distances,Double.POSITIVE_INFINITY);
		openList.clear();
		
		openList.add(node);
		open[b] = true;

		distances[b] = 0;
		directions[b] = null;
		
		IndexNode focus;
		
		while ((focus = openList.poll())!=null)
		{
			
			for (Edge edge : focus.getEdges())
			{
				SingleEdge singleEdge = (SingleEdge)edge;
				IndexNode neighbour  = (IndexNode)(singleEdge.b);
				
				double focusDistance = distances[focus.getIndex()];
				
				if (trafficBias)
				{
					focusDistance += singleEdge.length/(singleEdge.getTraffic()+1);
				}
				else
				{
					focusDistance += singleEdge.length;
				}
								
				if (!closed[neighbour.getIndex()])
				{
					if (focusDistance < distances[neighbour.getIndex()])
					{
						if (open[neighbour.getIndex()])
						{
							openList.remove(neighbour);
						}
						else
						{
							open[neighbour.getIndex()] = true;
						}
						
						directions[neighbour.getIndex()] = singleEdge.getReverse();
						distances[neighbour.getIndex()] = focusDistance;
						
						openList.add(neighbour);
						
					}
				}
			}
			
		
			if (focus==to)
			{
				return getPath(from,to,directions,distances);
			}
			
			closed[focus.getIndex()] = true;
		}
		
		
		return null;

	}
	
	private List<SingleEdge> getPath(IndexNode from, IndexNode to, SingleEdge[] directions, double[] distances)
	{

		List<SingleEdge> singleEdges = new ArrayList<SingleEdge> ();
		
		IndexNode focus = to;

		while (focus!=from)
		{
			SingleEdge singleEdge = directions[focus.getIndex()];
			focus = (IndexNode)(singleEdge.b);
			
			singleEdges.add(singleEdge);
		}
		
		return singleEdges;

	}
	
	
	
	private void init(double [] array, double value)
	{
		for (int d=0; d<array.length; d++)
		{
			array[d] = value;
		}
	}
	
}
