package elder.manhattan.routines;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Tube;
import elder.network.Edge;

public class Dijkstra implements Routine
{

	private Tube [][] directions;
	private double [][] distances;

	
	@Override
	public String run(Simulation simulation)
	{
		int noStations = simulation.getCity().getStations().size();
		
		List<Block> stations = simulation.getCity().getStations();
		
		int [] open = new int[noStations];
		int [] closed = new int[noStations];
		
		
		final Tube [] directions = new Tube[noStations];
		final double [] distances = new double[noStations];
		
		this.directions = new Tube[noStations][noStations];
		this.distances = new double[noStations][noStations];
		
		PriorityQueue<Block> openList = new PriorityQueue<Block> (new Comparator<Block>() 
		{

			@Override
			public int compare(Block a, Block b)
			{
				if (distances[a.getStation()]<distances[b.getStation()])
				{
					return -1;
				}
				else if (distances[a.getStation()]>distances[b.getStation()])
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
		
		for (int s=0; s<noStations; s++)
		{
			session++;
			
			init(distances,Double.POSITIVE_INFINITY);
			openList.clear();
			
			openList.add(stations.get(s));
			open[s] = session;

			distances[s] = 0;
			directions[s] = null;
			
			Block focus;
			
			while ((focus = openList.poll())!=null)
			{
				for (Edge edge : focus.getEdges())
				{
					Tube tube = (Tube)edge;
					Block neighbour  = tube.getTo();
					
					double focusDistance = distances[focus.getStation()] + (tube.length/tube.getSpeed());
					
					if (closed[neighbour.getStation()]!=session)
					{
						if (focusDistance < distances[neighbour.getStation()])
						{
							if (open[neighbour.getStation()]==session)
							{
								openList.remove(neighbour);
							}
							else
							{
								open[neighbour.getStation()] = session;
							}
							
							directions[neighbour.getStation()] = tube.getReverse();
							distances[neighbour.getStation()] = focusDistance;
							
							openList.add(neighbour);
							
						}
					}
				}
				
				closed[focus.getStation()] = session;
			}
			
			for (int s2=0; s2<noStations; s2++)
			{
				this.directions[s][s2] = directions[s2];
				this.distances[s][s2] = distances[s2];
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
	
	public Tube [][] getDirections()
	{
		return directions;
	}
	
	
	public double [][] getDistances()
	{
		return distances;
	}
}
