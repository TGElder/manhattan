package elder.manhattan.routines;

import java.util.HashSet;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.IndexNode;
import elder.manhattan.MultiEdge;
import elder.manhattan.Railway;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;
import elder.manhattan.Station;
import elder.network.Edge;

public class PlaceTraffic implements Routine
{

	private final Dijkstra roadDijkstra;
	private final Dijkstra railDijkstra;
	
	private int maxTraffic=0;
	
	public PlaceTraffic(Dijkstra roadDijkstra, Dijkstra railDijkstra)
	{
		this.roadDijkstra = roadDijkstra;
		this.railDijkstra = railDijkstra;
	}
	
	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
		
		for (IndexNode node : city.getRailwayNodes())
		{
			for (Edge edge : node.getEdges())
			{
				for (SingleEdge singleEdge : ((MultiEdge)edge).getEdges())
				{
					singleEdge.resetTraffic();
				}
				
			}
		}
		
		for (IndexNode node : city.getHighwayNodes())
		{
			for (Edge edge : node.getEdges())
			{
				for (SingleEdge singleEdge : ((MultiEdge)edge).getEdges())
				{
					singleEdge.resetTraffic();
				}
				
			}
		}
		
		maxTraffic = 0;
		
		int count=0;

		
		
		for (Block block : city.getBlocks())
		{
			
			
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
										
					double distance = roadDijkstra.getDistances()[block.getHighwayNode().getIndex()][focus.getHighwayNode().getIndex()];
					//double distance = Double.POSITIVE_INFINITY;
					
					Station from=null;
					Station to=null;
					
					for (Station s : block.getStations())
					{
						for (Station s2: focus.getStations())
						{
							double focusDistance = railDijkstra.getDistances()[s.getIndex()][s2.getIndex()];
															
							if (focusDistance<distance)
							{
								from = s;
								to = s2;
								distance = focusDistance;
							}
							
						}
					}
					
					List<MultiEdge> path;
					
					if (from!=null)
					{
						path = railDijkstra.getPath(from, to);
												
					}
					else
					{
						path = roadDijkstra.getPath(block.getHighwayNode(), focus.getHighwayNode());
					}
					
					
					if (path!=null)
					{
						
						for (MultiEdge multiEdge : path)
						{
							for (SingleEdge singleEdge : multiEdge.getEdges())
							{
								singleEdge.addTraffic(commuters[b]);
								maxTraffic = Math.max(maxTraffic, singleEdge.getTraffic());

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
