package elder.manhattan.layers;


import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.SelectionListener;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;

public class SelectedStationCoverage extends CityDrawerLayer implements SelectionListener<Block>
{
	private final City city;

	public SelectedStationCoverage(City city)
	{
		super("Selected Station Coverage");
		this.city = city;
	}


	private Block selectedBlock;

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
			if (selectedBlock.hasStation())
			{
				for (Block block : city.getBlocks())
				{
					if (coveredBy(block,selectedBlock.getStation()))
					{
						drawPolygon(block.getPolygon(),1f,1f,0,0.5f,true);
					}
				}
			}
		}
		
		
	}
	
	private boolean coveredBy(Block block, Station station)
	{
		
		for (Station other : block.getStations())
		{
			if (other==station)
			{
				return true;
			}
		}
		
		return false;
		
	}


	
}
