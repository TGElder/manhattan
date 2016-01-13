package elder.manhattan.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.SelectionListener;
import elder.manhattan.Simulation;
import elder.manhattan.graphics.CityDrawerLayer;


public class StationBuilder extends Mode implements SelectionListener<Block>,Routine
{
	
	private final List<Block> toBuild = new ArrayList<Block> ();
	
	private Block to=null;

	public StationBuilder()
	{
		super("Station Builder");
	}
	
	@Override
	public void onSelect(Block selection)
	{
		to = selection;
		
		refresh();
	}

	@Override
	public void onMove(Point cityPoint)
	{
		
	}

	@Override
	public void onLeftClick(Point cityPoint)
	{
		toBuild.add(to);
		
		refresh();

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

	@Override
	public String run(Simulation simulation)
	{
		for (Block block : toBuild )
		{
			simulation.getCity().createStation(block);
		}
		
		toBuild.clear();

		
		return null;
	}

	@Override
	public void draw()
	{
		
		if (to!=null)
		{
			drawPolygon(to.getPolygon(),1f,1f,0,0.5f,true);
			
			for (Block block : to.getNeighbours())
			{
				
				drawPolygon(block.getPolygon(),1f,1f,0,0.5f,true);
				
			}
		}
		
		
	}


}
