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
import elder.manhattan.Tube;
import elder.manhattan.Tubeway;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.network.Edge;
import elder.network.Node;

public class TubeBuilder extends Mode implements SelectionListener<Block>,Routine
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
			Edge existing;
			if ((existing = tube.getFrom().getEdge(tube.getTo()))!=null)
			{
				if (!hasService(tube,simulation))
				{
					tube.getFrom().removeEdge(existing);
				}
				
				
			}
			else
			{
				tube.getFrom().addEdge(tube);
			}
		}
		
		newTubes.clear();
		
		refresh();
		
		return null;
	}
	
	private boolean hasService(Tube tube, Simulation simulation)
	{
		for (Node node : simulation.getCity().getRailwayNodes())
		{
			for (Edge edge : node.getEdges())
			{
				Tubeway tubeway = (Tubeway)edge;
				for (Tube tubewayTube: tubeway.getTubes())
				{
					if (tubewayTube.equals(tube))
					{
						return true;
					}
				}
			}
		}
		return false;
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
