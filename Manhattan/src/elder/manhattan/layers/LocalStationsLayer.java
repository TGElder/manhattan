package elder.manhattan.layers;


import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.SelectionListener;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;

public class LocalStationsLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	
	public LocalStationsLayer()
	{
		super("Local Stations");
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
			for (Station station : selectedBlock.getStations())
			{
				drawPoint(station,1f,0f,0f,4f);
			}
		}
		
		
	}


	
}
