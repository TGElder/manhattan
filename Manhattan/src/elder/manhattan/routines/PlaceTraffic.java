package elder.manhattan.routines;

import java.util.HashSet;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.RailwayEdge;
import elder.manhattan.RailwayNode;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;
import elder.manhattan.Station;
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
				for (SingleEdge singleEdge : ((RailwayEdge)edge).getEdges())
				{
					singleEdge.resetTraffic();
				}
				
			}
		}
		
		maxTraffic = 0;
		
		int count=0;

		
		
		for (Block block : city.getBlocks())
		{
			for (Edge edge : block.getTrackNode().getEdges())
			{
				((SingleEdge)edge).resetTraffic();
			}
			
			if (!block.getResidents().isEmpty())
			{
				
				
				int [] commuters = new int[city.getBlocks().length]; 
				
				HashSet<Integer> commuted = new HashSet<Integer> ();
				
				for (Commute commute : block.getResidents())
				{
					int index = commute.getOffice().getTrackNode().getIndex();
					
					if (index!=block.getTrackNode().getIndex())
					{	
						commuters[index] ++;
						commuted.add(index);
					}
				}
				
				
				for (Integer b : commuted)
				{
					
					count++;
					Block focus = city.getBlocks()[b];
					
					double distance = (Math.abs(block.getRoadNode().x - focus.getRoadNode().x) + Math.abs(block.getRoadNode().y - focus.getRoadNode().y));
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
						List<RailwayEdge> path = dijkstra.getPath(from, to);
						//List<Tubeway> path = Collections.emptyList();
													
						if (path!=null)
						{
							
							for (RailwayEdge railwayEdge : path)
							{
								for (SingleEdge singleEdge : railwayEdge.getEdges())
								{
									singleEdge.addTraffic(commuters[b]);
									maxTraffic = Math.max(maxTraffic, singleEdge.getTraffic());
	
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
