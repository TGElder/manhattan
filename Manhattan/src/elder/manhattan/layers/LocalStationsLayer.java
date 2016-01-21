package elder.manhattan.layers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.IndexNode;
import elder.manhattan.SelectionListener;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.routines.Dijkstra;

public class LocalStationsLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	private City city;
	private Dijkstra roadDijkstra;
	private Dijkstra railDijkstra;
	private double threshold;
	
	public LocalStationsLayer(City city, Dijkstra railDijkstra, Dijkstra roadDijkstra, double threshold)
	{
		super("Local Stations");
		this.city = city;
		this.railDijkstra = railDijkstra;
		this.roadDijkstra = roadDijkstra;
		this.threshold = threshold;
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
			ArrayList<Station> local = new ArrayList<Station> ();
			
			for (IndexNode node : city.getRailwayNodes())
			{
				if (node instanceof Station)
				{
					Station station = (Station)node;
					
					double distance = roadDijkstra.getDistances()[selectedBlock.getHighwayNode().getIndex()][station.getBlock().getHighwayNode().getIndex()];
					
					if (distance<threshold)
					{
						local.add(station);
					}
				}
			}
			
			Collection<Station> toRemove = new HashSet<Station> ();
			
			for (Station station : local)
			{
				double distance = roadDijkstra.getDistances()[selectedBlock.getHighwayNode().getIndex()][station.getBlock().getHighwayNode().getIndex()];
				
				
				
				for (Station other : local)
				{
					double station2other = railDijkstra.getDistances()[station.getIndex()][other.getIndex()];
					double otherDistance = roadDijkstra.getDistances()[selectedBlock.getHighwayNode().getIndex()][other.getBlock().getHighwayNode().getIndex()];
					
					if (distance+station2other<otherDistance)
					{
						toRemove.add(other);
					}

				}
				
			}
			
			local.removeAll(toRemove);
			
			for (Station station : local)
			{
				drawPoint(station,1f,0f,0f,4f);
			}
		}
		
		
	}


	
}
