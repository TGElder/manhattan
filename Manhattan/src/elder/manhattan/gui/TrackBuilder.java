package elder.manhattan.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import elder.geometry.Line;
import elder.geometry.Point;
import elder.geometry.Polygon;
import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Routine;
import elder.manhattan.SelectionListener;
import elder.manhattan.Simulation;

public class TrackBuilder extends Mode implements SelectionListener<Block>, Routine
{
	
	private final List<NewTrack> newTrack = new ArrayList<NewTrack> ();
	private Block from=null;
	private Block to=null;
	
	private NewTrack next;
	
	private City city;
	
	private double buildCostPerKilometre;
	private double buildCostPerBuiltUpKilometre;
	private double removalCostPerKilometre;
	
	public TrackBuilder(City city, double buildCostPerKilometre, double buildCostPerBuiltUpKilometre, double removalCostPerKilometre)
	{
		super("Track Builder");
		this.city = city;
		this.buildCostPerKilometre = buildCostPerKilometre;
		this.buildCostPerBuiltUpKilometre = buildCostPerBuiltUpKilometre;
		this.removalCostPerKilometre = removalCostPerKilometre;
	}
	
	@Override
	public void onSelect(Block selection)
	{
		to = selection;
		
		if ( (from!=null&&to!=null) && (from!=to))
		{
			next = new NewTrack(from,to);
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
		if (next!=null)
		{
			newTrack.add(next);
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
		
		next=null;
		
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
		
		
		for (NewTrack track : newTrack)
		{
			if (track.getCost()<=city.getWallet().getMoney())
			{
			
				city.getWallet().addMoney(-track.getCost());
				
				if (track.remove)
				{
					try
					{
						simulation.getCity().removeTrack(track.from, track.to);
					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
					}
				}
				else
				{
					simulation.getCity().createTrack(track.from, track.to);
					
					
				}
			}
			else
			{
				System.out.println("Cannot afford £"+track.getCost()+"!");
			}
				
			
		}
		
		newTrack.clear();
		
		refresh();
		
		return null;
	}
	
	

	@Override
	public void draw()
	{
		
		
		if (next!=null)
		{
			
			if (next.remove)
			{
				drawLine(next.line,1f,0f,0f,2f,false);
			}
			else
			{
				drawLine(next.line,0f,0f,0f,2f,false);
				
				for (Line line : next.builtUpTrack)
				{
					drawLine(line,0f,0f,1f,2f,false);
				}
				
				for (Block block : next.builtUpBlocksIntersected)
				{
					drawPolygon(block.getPolygon(),0f,0f,1f,1f,false);

				}
				
				
			}
			
			drawText("£"+next.getCost(),16,next.to.getTrackNode());


		}
		
	
	}
	
	private Collection<Point> getIntersects(Polygon polygon, Line line)
	{
		Collection<Point> out = new HashSet<Point> ();
		
		for (Line edge : polygon)
		{
			Point intersect = line.getIntersectionInBothSegments(edge);
			
			if (intersect!=null)
			{
				out.add(intersect);
			}
		}
		
		return out;
	}
	
	
	@Override
	public void reset()
	{
		from=null;
		newTrack.clear();
	}
	
	private class NewTrack
	{
		
		Block from;
		Block to;
		
		boolean remove;
		
		Line line;
		List<Line> builtUpTrack = new ArrayList<Line> ();
		List<Block> builtUpBlocksIntersected = new ArrayList<Block> ();
		double length=0;
		double builtUpLength=0;
		
		public NewTrack(Block from, Block to)
		{
			
			
			this.from = from;
			this.to = to;
			
			remove = city.hasTrack(from, to);
			
			line = new Line(from.getTrackNode(),to.getTrackNode());
			
			length = line.length;
			
			if (!remove)
			{
			
				int minX = Math.min(from.getX(), to.getX());
				int maxX = Math.max(from.getX(), to.getX());
				int minY = Math.min(from.getY(), to.getY());
				int maxY = Math.max(from.getY(), to.getY());
				
				for (int x=minX; x<=maxX; x++)
				{
					for (int y=minY; y<=maxY; y++)
					{
						Collection<Point> intersects = getIntersects(city.getBlock(x, y).getPolygon(),line);
						
						if (intersects.size()==2)
						{
							List<Point> intersectList = new ArrayList<Point> (intersects);
							
							if (city.getBlock(x, y).isBuilt())
							{
								Line blockLine = new Line(intersectList.get(0),intersectList.get(1));
								builtUpTrack.add(blockLine);
								builtUpLength += blockLine.length;
								builtUpBlocksIntersected.add(city.getBlock(x, y));
							}
						}
					}
				}
				
				if (from.isBuilt())
				{
					builtUpBlocksIntersected.add(from);
					
					Collection<Point> intersects = getIntersects(from.getPolygon(),line);
					
					assert(intersects.size()==1);
					
					List<Point> intersectList = new ArrayList<Point> (intersects);
					Line blockLine = new Line(intersectList.get(0),from.getTrackNode());
					builtUpTrack.add(blockLine);
					builtUpLength += blockLine.length;
					
				}
				
				if (to.isBuilt())
				{
					builtUpBlocksIntersected.add(to);
					
					Collection<Point> intersects = getIntersects(to.getPolygon(),line);
					
					assert(intersects.size()==1);
					
					List<Point> intersectList = new ArrayList<Point> (intersects);
					Line blockLine = new Line(intersectList.get(0),to.getTrackNode());
					builtUpTrack.add(blockLine);
					builtUpLength += blockLine.length;
				}
			
			}
			
		}
		
		public int getCost()
		{
			if (remove)
			{
				return (int)Math.ceil(length*removalCostPerKilometre);
			}
			else
			{
				return (int)Math.ceil(length*buildCostPerKilometre + builtUpLength*buildCostPerBuiltUpKilometre*10);
			}
			
		}
	}


}
