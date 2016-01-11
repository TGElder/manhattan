package elder.manhattan.gui;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Line;
import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.SelectionListener;
import elder.manhattan.Simulation;
import elder.manhattan.Tube;
import elder.manhattan.graphics.CityDrawerLayer;

public class TubeBuilder extends CityDrawerLayer implements SelectionListener<Block>,MouseListener,Routine
{
	
	private final List<Tube> newTubes = new ArrayList<Tube> ();
	private Block from=null;
	private Block to=null;
	
	public TubeBuilder()
	{
		super("Tube Builder");
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
			Tube fromTo = new Tube(from,to,1);
			Tube toFrom = new Tube(to,from,1);
			
			fromTo.setReverse(toFrom);
			toFrom.setReverse(fromTo);
			
			newTubes.add(fromTo);
			newTubes.add(toFrom);
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
		
		for (Tube tube : newTubes)
		{
			tube.getFrom().addEdge(tube);
		}
		
		newTubes.clear();
		
		refresh();
		
		return null;
	}

	@Override
	public void draw()
	{
		
		for (Tube tube : newTubes)
		{
			drawLine(tube,0f,0f,1f,2f,false);
		}
		
		
		
		if ( (from!=null&&to!=null) && (from!=to))
		{
			drawLine(new Line(from,to),0f,0f,1f,1f,false);
		}
		
	
	}



}
