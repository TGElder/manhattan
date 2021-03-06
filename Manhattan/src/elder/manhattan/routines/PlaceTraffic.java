package elder.manhattan.routines;

import java.util.HashSet;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.IndexNode;
import elder.manhattan.MultiEdge;
import elder.manhattan.Pathfinder;
import elder.manhattan.Pathfinder.Journey;
import elder.manhattan.Pathfinder.Journey.Leg;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;
import elder.network.Edge;

public class PlaceTraffic implements Routine
{

	private Pathfinder pathfinder;
	
	private int railPassengers=0;
	private double railDistance=0;
	private int maxTraffic=0;
	
	public PlaceTraffic(Pathfinder pathfinder)
	{
		this.pathfinder = pathfinder;
	}
	
	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
		
		railPassengers=0;
		railDistance=0;
		
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
					
					Block focus = city.getBlocks()[b];
										
					Journey journey = pathfinder.new Journey(block,focus);
					pathfinder.computeDistance(journey);
					pathfinder.computePaths(journey);
					
					for (Leg leg : journey.getLegs())
					{
						placeTraffic(leg.getPath(),leg.getMode(),commuters[b]);
						
						if (leg.getMode()==SingleEdge.RAIL)
						{
							railPassengers+=commuters[b];
							
							double railDistance = leg.getDistance()*commuters[b];
							
							this.railDistance+=railDistance;
							city.getWallet().addMoney((int)(railDistance/10));
						}
					}
					
				}
				
			}
		}
		
		return null;
		
	}
	
	private void placeTraffic(List<MultiEdge> path, int type, int traffic)
	{
		if (path!=null)
		{
			
			for (MultiEdge multiEdge : path)
			{
				for (SingleEdge singleEdge : multiEdge.getEdges())
				{
					singleEdge.addTraffic(type,traffic);
					singleEdge.getReverse().addTraffic(type,traffic);
					maxTraffic = Math.max(maxTraffic, singleEdge.getTraffic(type));

				}
				
			}
		}
	}
	
	public int getMaxTraffic()
	{
		return maxTraffic;
	}
	
	public int getRailPassengers()
	{
		return railPassengers;
	}
	
	public double getRailDistance()
	{
		return railDistance;
	}
	

	
	 

}
