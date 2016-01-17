package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import elder.manhattan.RailwayNode;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.Section;
import elder.network.Edge;
import elder.network.Node;

public class Dijkstra implements Routine
{

	private Section [][] directions;
	private double [][] distances;

	
	@Override
	public String run(Simulation simulation)
	{
		int noNodes = simulation.getCity().getRailwayNodes().size();
		
		List<RailwayNode> nodes = simulation.getCity().getRailwayNodes();
		
		int [] open = new int[noNodes];
		int [] closed = new int[noNodes];
	
		final double [] distances = new double[noNodes];
		
		this.directions = new Section[noNodes][noNodes];
		this.distances = new double[noNodes][noNodes];
		
		PriorityQueue<RailwayNode> openList = new PriorityQueue<RailwayNode> (new Comparator<RailwayNode>() 
		{

			@Override
			public int compare(RailwayNode a, RailwayNode b)
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
			if (nodes.get(s) instanceof Station) // Not a platform
			{
			
				session++;
				
				init(distances,Double.POSITIVE_INFINITY);
				final Section [] directions = new Section[noNodes];
	
				openList.clear();
				
				openList.add(nodes.get(s));
				open[s] = session;
	
				distances[s] = 0;
				directions[s] = null;
				
				RailwayNode focus;
				
				while ((focus = openList.poll())!=null)
				{
					for (Edge edge : focus.getEdges())
					{
						Section tube = (Section)edge;
						Station neighbour  = tube.getTo();
						
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
								
								directions[neighbour.getIndex()] = tube.getReverse();
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
		}
		
		return null;
	}
	
	private void init(double [] array, double value)
	{
		for (int d=0; d<array.length; d++)
		{
			array[d] = value;
		}
	}
	
	public Section [][] getDirections()
	{
		return directions;
	}
	
	
	public double [][] getDistances()
	{
		return distances;
	}
	
	public List<Section> getPath(Station from, Station to)
	{
		int focus = from.getIndex();
		
		Section edge;
		
		List<Section> out = new ArrayList<Section> ();
		
		while ((edge = getDirections()[to.getIndex()][focus])!=null)
		{
			out.add(edge);
			focus = ((Station)edge.b).getIndex();
		}
		
		return out;
	}
}
