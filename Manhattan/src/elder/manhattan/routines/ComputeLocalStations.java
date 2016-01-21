package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.IndexNode;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;

public class ComputeLocalStations implements Routine
{
	
	private Dijkstra roadDijkstra;
	private Dijkstra railDijkstra;
	private double threshold;

	public ComputeLocalStations(Dijkstra railDijkstra, Dijkstra roadDijkstra, double threshold)
	{
		this.railDijkstra = railDijkstra;
		this.roadDijkstra = roadDijkstra;
		this.threshold = threshold;
	}
	
	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
		
		for (Block block : simulation.getCity().getBlocks())
		{
			ArrayList<Station> local = new ArrayList<Station> ();
			
			for (IndexNode node : city.getRailwayNodes())
			{
				if (node instanceof Station)
				{
					Station station = (Station)node;
					
					double distance = roadDijkstra.getDistances()[block.getHighwayNode().getIndex()][station.getBlock().getHighwayNode().getIndex()];
					
					if (distance<threshold)
					{
						local.add(station);
					}
				}
			}
			
			Collection<Station> toRemove = new HashSet<Station> ();
			
			for (Station station : local)
			{
				double distance = roadDijkstra.getDistances()[block.getHighwayNode().getIndex()][station.getBlock().getHighwayNode().getIndex()];
				
				
				
				for (Station other : local)
				{
					double station2other = railDijkstra.getDistances()[station.getIndex()][other.getIndex()];
					double otherDistance = roadDijkstra.getDistances()[block.getHighwayNode().getIndex()][other.getBlock().getHighwayNode().getIndex()];
					
					if (distance+station2other<otherDistance)
					{
						toRemove.add(other);
					}

				}
				
			}
			
			local.removeAll(toRemove);
			
			
		}
		
		return null;
	}

}
