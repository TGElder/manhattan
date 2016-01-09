package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.SelectionListener;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.network.Edge;

public class StationLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	
	public StationLayer()
	{
		super("Station Layer");
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
			
			if (selectedBlock.hasStation())
			{
				for (Edge edge : selectedBlock.getStation().getEdges())
				{
					
					drawLine(edge,1f,1f,1f,6f,false);
				
				}
			}
		}
	}


	
}
