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
	private final Dijkstra railDijkstra;
	private final Dijkstra roadDijkstra;
	
	public TimeLayer(City city, Dijkstra railDijkstra, Dijkstra roadDijkstra)
	{
		super("Time Layer");
		this.city = city;
		this.railDijkstra = railDijkstra;
		this.roadDijkstra = roadDijkstra;
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
				double distance = roadDijkstra.getDistances()[selectedBlock.getHighwayNode().getIndex()][focus.getHighwayNode().getIndex()];
				
				for (Station s : selectedBlock.getStations())
				{
					for (Station s2: focus.getStations())
					{
						double toStation = roadDijkstra.getDistances()[selectedBlock.getHighwayNode().getIndex()][s.getBlock().getHighwayNode().getIndex()];
						double station2station = railDijkstra.getDistances()[s.getIndex()][s2.getIndex()];
						double fromStation = roadDijkstra.getDistances()[s2.getBlock().getHighwayNode().getIndex()][selectedBlock.getHighwayNode().getIndex()];
						distance = Math.min(distance, toStation + station2station + fromStation);
					}
				}
				
				if (distance!=Double.POSITIVE_INFINITY)
				{
					drawPolygon(focus.getPolygon(),(float)(distance/maxDistance),(float)(distance/maxDistance),(float)(distance/maxDistance),0.25f,true);
				}
			}
				
			
			
		}
	}


	
}
