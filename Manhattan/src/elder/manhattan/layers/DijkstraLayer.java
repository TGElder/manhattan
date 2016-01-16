package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.SelectionListener;
import elder.manhattan.Track;
import elder.manhattan.Section;
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
					if (selectedBlock.getStation().getIndex()<dijkstra.getDirections().length)
					{
					
						Section [] tubeways = dijkstra.getDirections()[selectedBlock.getStation().getIndex()];
						
						if (tubeways!=null)
						{
						
							for (Section section : tubeways)
							{
								if (section!=null)
								{
									for (Track track : section.getTubes())
									{
											drawLine(track,1f,1f,1f,6f,false);
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
