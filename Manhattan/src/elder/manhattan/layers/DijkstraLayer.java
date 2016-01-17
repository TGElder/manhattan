package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.RailwayEdge;
import elder.manhattan.SelectionListener;
import elder.manhattan.SingleEdge;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.routines.Dijkstra;

public class DijkstraLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	private final Dijkstra dijkstra;
	
	public DijkstraLayer(Dijkstra dijkstra)
	{
		super("Dijkstra Layer");
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
			
			if (selectedBlock.hasStation())
			{
				if (dijkstra.getDirections()!=null)
				{
					if (selectedBlock.getStation().getIndex()!=null||selectedBlock.getStation().getIndex()<dijkstra.getDirections().length)
					{
					
						RailwayEdge [] tubeways = dijkstra.getDirections()[selectedBlock.getStation().getIndex()];
						
						if (tubeways!=null)
						{
						
							for (RailwayEdge railwayEdge : tubeways)
							{
								if (railwayEdge!=null)
								{
									for (SingleEdge singleEdge : railwayEdge.getEdges())
									{
											drawLine(singleEdge,1f,1f,1f,6f,false);
									}
								}
							}
						
						}
						
					}
					
				}
			}
		}
	}


	
}
