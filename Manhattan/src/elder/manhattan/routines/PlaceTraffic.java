package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.RailwayNode;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.Tube;
import elder.manhattan.Tubeway;
import elder.network.Edge;

public class PlaceTraffic implements Routine
{

	private final Dijkstra dijkstra;
	
	private int maxTraffic=0;
	
	public PlaceTraffic(Dijkstra dijkstra)
	{
		this.dijkstra = dijkstra;
	}
	
	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
		
		for (RailwayNode node : city.getRailwayNodes())
		{
			for (Edge edge : node.getEdges())
			{
				for (Tube tube : ((Tubeway)edge).getTubes())
				{
					tube.resetTraffic();
				}
				
			}
		}
		
		maxTraffic = 0;
		
		int count=0;

		
		
		for (Block block : city.getBlocks())
		{
			for (Edge edge : block.getEdges())
			{
				((Tube)edge).resetTraffic();
			}
			
			if (!block.getResidents().isEmpty())
			{
				
				
				int [] commuters = new int[city.getBlocks().length]; 
				
				HashSet<Integer> commuted = new HashSet<Integer> ();
				
				for (Commute commute : block.getResidents())
				{
					int index = commute.getOffice().getIndex();
					
					if (index!=block.getIndex())
					{	
						commuters[index] ++;
						commuted.add(index);
					}
				}
				
				
				for (Integer b : commuted)
				{
					
					count++;
					Block focus = city.getBlocks()[b];
					
					double distance = (Math.abs(block.x - focus.x) + Math.abs(block.y - focus.y));
					//double distance = Double.POSITIVE_INFINITY;
					
					Station from=null;
					Station to=null;
					
					for (Station s : block.getStations())
					{
						for (Station s2: focus.getStations())
						{
							double focusDistance = dijkstra.getDistances()[s.getIndex()][s2.getIndex()];
															
							if (focusDistance<distance)
							{
								from = s;
								to = s2;
								distance = focusDistance;
							}
							
						}
					}
					
					if (from!=null)
					{
						List<Tubeway> path = dijkstra.getPath(from, to);
						//List<Tubeway> path = Collections.emptyList();
													
						if (path!=null)
						{
							
							for (Tubeway tubeway : path)
							{
								for (Tube tube : tubeway.getTubes())
								{
									tube.addTraffic(commuters[b]);
									maxTraffic = Math.max(maxTraffic, tube.getTraffic());
	
								}
								
							}
						}
					}
				}
				
			}
		}
		
		return count+"";
		
		
	}
	
	public int getMaxTraffic()
	{
		return maxTraffic;
	}

}
