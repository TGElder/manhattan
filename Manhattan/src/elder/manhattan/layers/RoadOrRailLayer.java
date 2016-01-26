package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Pathfinder;
import elder.manhattan.Pathfinder.Journey;
import elder.manhattan.SelectionListener;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.routines.Dijkstra;

public class RoadOrRailLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	private final City city;
	private final Pathfinder pathfinder;
	
	public RoadOrRailLayer(City city, Pathfinder pathfinder)
	{
		super("Road or Rail");
		this.city = city;
		this.pathfinder = pathfinder;
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
			
			
			for (Block focus : city.getBlocks())
			{
				Journey journey = pathfinder.new Journey(selectedBlock,focus);
				pathfinder.computeDistance(journey);
				
				if (journey.getLegs().length==1)
				{
					drawPolygon(focus.getPolygon(),0f,0f,0f,0.25f,true);
				}
				
				
			}
				
			
			
		}
	}


	
}
