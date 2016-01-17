package elder.manhattan.gui;

import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.Selection;
import elder.manhattan.Simulation;

public class HoverSelector implements MouseListener
{
	
	private final Simulation sim;
	private final Selection<Block> selectedBlock = new Selection<Block> ();
	
	public HoverSelector(Simulation sim)
	{
		this.sim = sim;
	}
	
	@Override
	public void onMove(Point cityPoint)
	{
		int x = (int) (cityPoint.x/sim.getCity().getScale());
		int y = (int) (cityPoint.y/sim.getCity().getScale());

		if (x>0&&x<sim.getCity().getWidth()&&y>0&&y<sim.getCity().getHeight())
		{
			selectedBlock.setSelection(sim.getCity().getBlock(x, y));
		}
		else
		{
			selectedBlock.setSelection(null);
		}
		
	}

	@Override
	public void onLeftClick(Point cityPoint)
	{
		
	}

	@Override
	public void onMiddleClick(Point cityPoint)
	{
		
	}

	@Override
	public void onRightClick(Point cityPoint)
	{
		
	}

	@Override
	public void onLeftDrag(Point screenOffset)
	{
		
	}

	@Override
	public void onMiddleDrag(Point screenOffset)
	{
		
	}

	@Override
	public void onRightDrag(Point screenOffset)
	{
		
	}

	@Override
	public void onWheelUp()
	{
		
	}

	@Override
	public void onWheelDown()
	{
		
	}
	
	
	public Selection<Block> getSelectedBlock()
	{
		return selectedBlock;
	}


}