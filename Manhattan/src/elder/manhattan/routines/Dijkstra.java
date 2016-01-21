package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import elder.manhattan.IndexNode;
import elder.manhattan.MultiEdge;
import elder.manhattan.Railway;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.TimeEdge;
import elder.network.Edge;

public class Dijkstra implements Routine
{

	private MultiEdge [][] directions;
	private double [][] distances;
	
	private final List<? extends IndexNode> nodes;
	
	public Dijkstra(List<? extends IndexNode> nodes)
	{
		this.nodes = nodes;
	}

	
	@Override
	public String run(Simulation simulation)
	{
		int noNodes = nodes.size();
				
		int [] open = new int[noNodes];
		int [] closed = new int[noNodes];
	
		final double [] distances = new double[noNodes];
		
		this.directions = new MultiEdge[noNodes][noNodes];
		this.distances = new double[noNodes][noNodes];
		
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
		
		
		int session=0;
		
		for (int s=0; s<noNodes; s++)
		{

			
//			if (nodes.get(s) instanceof Station) // Not a platform
//			{
			
				session++;
				
				init(distances,Double.POSITIVE_INFINITY);
				final MultiEdge [] directions = new MultiEdge[noNodes];
	
				openList.clear();
				
				openList.add(nodes.get(s));
				open[s] = session;
	
				distances[s] = 0;
				directions[s] = null;
				
				IndexNode focus;
				
				while ((focus = openList.poll())!=null)
				{
					for (Edge edge : focus.getEdges())
					{
						TimeEdge tube = (TimeEdge)edge;
						IndexNode neighbour  = (IndexNode)tube.b;
						
						double focusDistance = distances[focus.getIndex()] + (tube.getTime());
												
						if (closed[neighbour.getIndex()]!=session)
						{
							if (focusDistance < distances[neighbour.getIndex()])
							{
								if (open[neighbour.getIndex()]==session)
								{
									openList.remove(neighbour);
								}
								else
								{
									open[neighbour.getIndex()] = session;
								}
								
								directions[neighbour.getIndex()] = (MultiEdge)tube.getReverse();
								distances[neighbour.getIndex()] = focusDistance;
								
								openList.add(neighbour);
								
							}
						}
					}
					
					closed[focus.getIndex()] = session;
				}
				
				for (int s2=0; s2<noNodes; s2++)
				{
					this.directions[s][s2] = directions[s2];
					this.distances[s][s2] = distances[s2];
				}
			}
//		}
		
		return null;
	}
	
	private void init(double [] array, double value)
	{
		for (int d=0; d<array.length; d++)
		{
			array[d] = value;
		}
	}
	
	public MultiEdge [][] getDirections()
	{
		return directions;
	}
	
	
	public double [][] getDistances()
	{
		return distances;
	}
	
	public List<MultiEdge> getPath(IndexNode from, IndexNode to)
	{
		int focus = from.getIndex();
		
		MultiEdge edge;
		
		List<MultiEdge> out = new ArrayList<MultiEdge> ();
		
		while ((edge = getDirections()[to.getIndex()][focus])!=null)
		{
			out.add(edge);
			focus = ((IndexNode)edge.b).getIndex();
		}
		
		return out;
	}
}
