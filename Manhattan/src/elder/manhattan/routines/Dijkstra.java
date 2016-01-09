package elder.manhattan.routines;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.Tubeway;
import elder.network.Edge;

public class Dijkstra implements Routine
{

	private Tubeway [][] directions;
	private double [][] distances;

	
	@Override
	public String run(Simulation simulation)
	{
		int noStations = simulation.getCity().getStations().size();
		
		List<Station> stations = simulation.getCity().getStations();
		
		int [] open = new int[noStations];
		int [] closed = new int[noStations];
		
		
		final double [] distances = new double[noStations];
		
		this.directions = new Tubeway[noStations][noStations];
		this.distances = new double[noStations][noStations];
		
		PriorityQueue<Station> openList = new PriorityQueue<Station> (new Comparator<Station>() 
		{

			@Override
			public int compare(Station a, Station b)
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
		
		for (int s=0; s<noStations; s++)
		{
			if (stations.get(s).getBlock().getStation()==stations.get(s)) // Not a platform
			{
			
				session++;
				
				init(distances,Double.POSITIVE_INFINITY);
				final Tubeway [] directions = new Tubeway[noStations];
	
				openList.clear();
				
				openList.add(stations.get(s));
				open[s] = session;
	
				distances[s] = 0;
				directions[s] = null;
				
				Station focus;
				
				while ((focus = openList.poll())!=null)
				{
					for (Edge edge : focus.getEdges())
					{
						Tubeway tube = (Tubeway)edge;
						Station neighbour  = tube.getTo();
						
						double focusDistance = distances[focus.getIndex()] + (tube.length/tube.getSpeed());
						
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
				
				for (int s2=0; s2<noStations; s2++)
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
	
	public Tubeway [][] getDirections()
	{
		return directions;
	}
	
	
	public double [][] getDistances()
	{
		return distances;
	}
}
