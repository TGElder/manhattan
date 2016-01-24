package elder.manhattan.layers;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.MultiEdge;
import elder.manhattan.Pathfinder;
import elder.manhattan.Pathfinder.Journey;
import elder.manhattan.Pathfinder.Journey.Leg;
import elder.manhattan.Railway;
import elder.manhattan.SelectionListener;
import elder.manhattan.SingleEdge;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.routines.Dijkstra;
import elder.manhattan.routines.PlaceTraffic;


public class CommuteLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	private final City city;
	
	private final Pathfinder pathfinder;
	private final PlaceTraffic traffic;


	
	public CommuteLayer(City city, Pathfinder pathfinder, PlaceTraffic traffic)
	{
		super("Commute Layer");
		this.city = city;
		this.pathfinder = pathfinder;
		this.traffic = traffic;
	}

	@Override
	public void onSelect(Block selection)
	{
		selectedBlock = selection;
		refresh();
	}
	

	@Override
	public void draw()
	{
		
		if (selectedBlock!=null)
		{
			
			if (!selectedBlock.getResidents().isEmpty())
			{
				
				
				int [] commuters = new int[city.getBlocks().length]; 
				
				HashSet<Integer> commuted = new HashSet<Integer> ();
				
				for (Commute commute : selectedBlock.getResidents())
				{
					if (commute.getOffice()!=null)
					{
					
						int index = commute.getOffice().getTrackNode().getIndex();
						
						if (index!=selectedBlock.getTrackNode().getIndex())
						{	
							commuters[index] ++;
							commuted.add(index);
						}
					}
				}
				
				
				for (Integer b : commuted)
				{
					
					Block focus = city.getBlocks()[b];
					
					Journey journey = pathfinder.new Journey(selectedBlock,focus);
					pathfinder.computeDistance(journey);
					pathfinder.computePaths(journey);
					
					for (Leg leg : journey.getLegs())
					{
						for (MultiEdge multiEdge : leg.getPath())
						{
							float R,G,B;
							if (multiEdge instanceof Railway)
							{
								Railway railway = (Railway)multiEdge;
								Color color = railway.getService().getLine().getColor();
								R = color.getRed()/255f;
								G = color.getGreen()/255f;
								B = color.getBlue()/255f;
							}
							else
							{
								R = 1f;
								G = 1f;
								B = 1f;
							}
							
							for (SingleEdge singleEdge : multiEdge.getEdges())
							{
								float width = 1 + (commuters[b]*9f)/(traffic.getMaxTraffic()*1f);
								
								drawLine(singleEdge,R,G,B,width,false);
								drawLine(singleEdge.getReverse(),R,G,B,width,false);

							}
							
						}
					}
					
					
				}
			
			}
		}
		
	}


}