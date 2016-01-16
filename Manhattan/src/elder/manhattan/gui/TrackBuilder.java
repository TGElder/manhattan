package elder.manhattan.gui;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Line;
import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.SelectionListener;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.Track;
import elder.manhattan.Section;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.network.Edge;
import elder.network.Node;

public class TrackBuilder extends Mode implements SelectionListener<Block>, Routine
{
	
	private final List<Block> fromBlocks = new ArrayList<Block> ();
	private final List<Block> toBlocks = new ArrayList<Block> ();
	private Block from=null;
	private Block to=null;
	
	public TrackBuilder()
	{
		super("Track Builder");
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
		if ( (from!=null&&to!=null) && (from!=to))
		{
			fromBlocks.add(from);
			toBlocks.add(to);
		}
		
		from = to;
		
		refresh();
	}

	@Override
	public void onMiddleClick(Point cityPoint)
	{

	}

	@Override
	public void onRightClick(Point cityPoint)
	{
		from=null;
		to=null;
		
		refresh();
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
		
		for (int b=0; b<fromBlocks.size(); b++)
		{
			try
			{
				simulation.getCity().toggleTrack(fromBlocks.get(b), toBlocks.get(b));
			} 
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		
		fromBlocks.clear();
		toBlocks.clear();
		
		refresh();
		
		return null;
	}
	
	

	@Override
	public void draw()
	{
		
		
		if ( (from!=null&&to!=null) && (from!=to))
		{
			drawLine(new Line(from,to),0f,0f,1f,1f,false);
		}
		
	
	}

	@Override
	public void reset()
	{
		from=null;
	}


}
