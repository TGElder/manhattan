package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.SelectionListener;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.routines.Dijkstra;

public class TimeLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	private final City city;
	private final Dijkstra dijkstra;
	
	public TimeLayer(City city, Dijkstra dijkstra)
	{
		super("Time Layer");
		this.city = city;
		this.dijkstra = dijkstra;
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
				double distance = Double.POSITIVE_INFINITY;
				
				if (dijkstra.getDirections()!=null)
				{
				
					for (Station s : selectedBlock.getStations())
					{
						for (Station s2: focus.getStations())
						{
							distance = Math.min(distance, dijkstra.getDistances()[s.getIndex()][s2.getIndex()]);
						}
					}
				
				}
				
				if (distance!=Double.POSITIVE_INFINITY)
				{
					drawPolygon(focus.getPolygon(),(float)(distance/maxDistance),(float)(distance/maxDistance),(float)(distance/maxDistance),true);
				}
			}
				
			
			
		}
	}


	
}
