package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.MultiEdge;
import elder.manhattan.Platform;
import elder.manhattan.SelectionListener;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.network.Edge;

public class RailwayLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	public RailwayLayer()
	{
		super("Railways");
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
				for (Platform platform : selectedBlock.getStation().getPlatforms())
				{
					for (Edge multiEdge : platform.getEdges())
					{
						for (Edge edge : ((MultiEdge)multiEdge).getEdges())
						{
							drawLine(edge,1f,0f,0f,2f,false);
						}
						
					}
				}
				
			}
		}
		
		
	}


	
}
