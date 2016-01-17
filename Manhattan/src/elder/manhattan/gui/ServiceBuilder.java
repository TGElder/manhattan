package elder.manhattan.gui;


import java.util.List;

import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Platform;
import elder.manhattan.Routine;
import elder.manhattan.SelectionListener;
import elder.manhattan.Service;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.SingleEdge;
import elder.manhattan.Pathfinder;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.network.Edge;


public class ServiceBuilder extends Mode implements SelectionListener<Block>,Routine
{
	
	private Station from=null;
	private Station to=null;

	
	private final City city;
	private final Pathfinder pathfinder;
		
	private Service service;
	
	public ServiceBuilder(City city)
	{
		super("Service Builder");
		this.city = city;
		pathfinder = new Pathfinder(city);
	}
	
	@Override
	public void onSelect(Block selection)
	{
				
		if (selection!=null)
		{
			if (selection.hasStation())
			{
				to = selection.getStation();
			}
			else
			{
				to = null;
			}
			
			
		}
		
		refresh();

	}

	@Override
	public void onMove(Point cityPoint)
	{
		
	}

	@Override
	public void onLeftClick(Point cityPoint)
	{
		if (service!=null)
		{
			if ( (from!=null&&to!=null) && (from!=to))
			{
				Platform fromPlatform = from.getPlatform(service);
				Platform toPlatform = to.getPlatform(service);
								
				if (fromPlatform!=null&&toPlatform!=null&&(fromPlatform.getEdge(toPlatform))!=null)
				{
					try 
					{
						service.unlink(city, fromPlatform, toPlatform);
					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
					}
				}
				else
				{
				
					List<SingleEdge> singleEdges = pathfinder.findPath(from.getBlock(), to.getBlock());
					
					if (singleEdges!=null)
					{
						try
						{
							service.link(city, from, to, singleEdges);
						}
						catch (Exception e)
						{
							System.out.println(e.getMessage());
						}
					}
					
				}
				
				
			}
			
			from = to;
		
			refresh();
		}
	}

	@Override
	public void onMiddleClick(Point cityPoint)
	{

	}

	@Override
	public void onRightClick(Point cityPoint)
	{
		from = null;
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
		
		
		return null;
	}

	@Override
	public void draw()
	{
		if (service!=null)
		{
	
			if ( (from!=null&&to!=null) && (from!=to))
			{
				List<SingleEdge> singleEdges = pathfinder.findPath(from.getBlock(), to.getBlock());
				
				if (singleEdges!=null)
				{
					for (SingleEdge singleEdge: singleEdges)
					{
						float R=service.getLine().getColor().getRed()/255f;
						float G=service.getLine().getColor().getGreen()/255f;
						float B=service.getLine().getColor().getBlue()/255f;
						drawLine(singleEdge,R,G,B,4f,false);
					}
				}
			}
			
		}
		
	
	}
	
	public Service getService()
	{
		return service;
	}
	
	public void setService(Service service)
	{
		this.service = service;
		reset();
	}

	@Override
	public void reset()
	{
		from=null;
	}



}
