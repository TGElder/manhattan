package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.SelectionListener;
import elder.manhattan.Tube;
import elder.manhattan.Tubeway;
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
				Tubeway [] tubeways = dijkstra.getDirections()[selectedBlock.getStation().getIndex()];
				
				for (Tubeway tubeway : tubeways)
				{
					if (tubeway!=null)
					{
						for (Tube tube : tubeway.getTubes())
						{
								drawLine(tube,1f,1f,1f,6f,false);
						}
					}
				}
			}
		}
	}


	
}
