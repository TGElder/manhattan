package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.SelectionListener;
import elder.manhattan.graphics.CityDrawerLayer;

public class SelectedBlockLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	public SelectedBlockLayer()
	{
		super("Selected Block");
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
			drawPolygon(selectedBlock.getPolygon(),1f,1f,1f,false);
		}
	}


	
}
