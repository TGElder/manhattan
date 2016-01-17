package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.MultiEdge;
import elder.manhattan.SelectionListener;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.network.Edge;

public class HighwayLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	public HighwayLayer()
	{
		super("Highways");
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
			if (selectedBlock.getHighwayNode()!=null)
			{
				for (Edge multiEdge : selectedBlock.getHighwayNode().getEdges())
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
