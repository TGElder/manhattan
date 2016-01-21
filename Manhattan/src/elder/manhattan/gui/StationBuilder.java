package elder.manhattan.gui;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.IndexNode;
import elder.manhattan.Routine;
import elder.manhattan.SelectionListener;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.routines.Dijkstra;


public class StationBuilder extends Mode implements SelectionListener<Block>,Routine
{
	
	private final List<Block> toBuild = new ArrayList<Block> ();
	
	private Block to=null;

	private final City city;
	private final Dijkstra roadDijkstra;
	private final double threshold;
	
	public StationBuilder(City city, Dijkstra roadDijkstra, double threshold)
	{
		super("Station Builder");
		this.city = city;
		this.roadDijkstra = roadDijkstra;
		this.threshold = threshold;
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
			
			try
			{
				simulation.getCity().toggleStation(block);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		
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
			
			for (Block block : city.getBlocks())
			{
				if (roadDijkstra.getDistances()[to.getHighwayNode().getIndex()][block.getHighwayNode().getIndex()]<=threshold)
				{
					drawPolygon(block.getPolygon(),1f,1f,0,0.25f,true);

				}
				
				
			}
		}
		
		
	}
	
	@Override
	public void reset()
	{

	}



}
