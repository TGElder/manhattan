package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Pathfinder;
import elder.manhattan.Pathfinder.Journey;
import elder.manhattan.SelectionListener;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.routines.Dijkstra;

public class TimeLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	private final City city;
	private final Pathfinder pathfinder;
	
	public TimeLayer(City city, Pathfinder pathfinder)
	{
		super("Time Layer");
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
			
			double maxDistance = Math.sqrt(Math.pow(city.getWidth(), 2)+Math.pow(city.getHeight(), 2));
			
			for (Block focus : city.getBlocks())
			{
				Journey journey = pathfinder.new Journey(selectedBlock,focus);
				pathfinder.computeDistance(journey);
				
				double distance = journey.getDistance();
				
				if (distance!=Double.POSITIVE_INFINITY)
				{
					distance = maxDistance - distance;
					drawPolygon(focus.getPolygon(),(float)(distance/maxDistance),(float)(distance/maxDistance),(float)(distance/maxDistance),0.5f,true);
				}
			}
				
			
			
		}
	}


	
}
