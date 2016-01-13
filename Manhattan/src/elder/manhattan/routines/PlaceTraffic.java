package elder.manhattan.routines;

import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
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
		
		for (Station station : city.getStations())
		{
			for (Edge edge : station.getEdges())
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
			
			int [] commuters = new int[city.getBlocks().length]; 
			
			for (Commute commute : block.getResidents())
			{
				commuters[commute.getOffice().getIndex()] ++;
			}
			
			
			for (int b=0; b<commuters.length; b++)
			{
				if (b!=block.getIndex())
				{
					if (commuters[b]>0)
					{
						count++;
						Block focus = city.getBlocks()[b];
						
						double distance = (Math.abs(block.x - focus.x) + Math.abs(block.y - focus.y));
						
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
			
		}
		
		return count+"";
		
		
	}
	
	public int getMaxTraffic()
	{
		return maxTraffic;
	}

}
