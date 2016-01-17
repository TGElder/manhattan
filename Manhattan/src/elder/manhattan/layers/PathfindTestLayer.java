package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.SelectionListener;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.routines.Dijkstra;
import elder.network.Edge;

public class PathfindTestLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	private final Dijkstra dijkstra;
	
	public PathfindTestLayer(Dijkstra dijkstra)
	{
		super("Pathfind Test Layer");
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
				
				int focus = selectedBlock.getStation().getIndex();
				
				Edge edge;
				
				while ((edge = dijkstra.getDirections()[200][focus])!=null)
				{
					drawLine(edge,1f,1f,1f,6f,false);
					focus = ((Station)edge.b).getIndex();
				}
				
			}
		}
	}


	
}
