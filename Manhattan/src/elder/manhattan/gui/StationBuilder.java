package elder.manhattan.gui;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.SelectionListener;
import elder.manhattan.Simulation;
import elder.manhattan.graphics.CityDrawerLayer;


public class StationBuilder extends CityDrawerLayer implements SelectionListener<Block>,MouseListener,Routine
{
	
	private final List<Block> newStations = new ArrayList<Block> ();
	private Block to=null;

	public StationBuilder()
	{
		super("Station Builder");
	}
	
	@Override
	public void onSelect(Block selection)
	{
		to = selection;
	}

	@Override
	public void onMove(Point cityPoint)
	{
		
	}

	@Override
	public void onLeftClick(Point cityPoint)
	{
		newStations.add(to);
		
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
		for (Block block : newStations )
		{
			simulation.getCity().createStation(block);
		}
		
		newStations.clear();

		
		return null;
	}

	@Override
	public void draw()
	{
		for (Block block : newStations)
		{
			drawPoint(block,0f,0f,0f,6f);
			drawPoint(block,1f,1f,1f,4f);
		}
	}


}
