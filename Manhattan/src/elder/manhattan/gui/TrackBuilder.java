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
		
		
		
		if ( (from!=null&&to!=null))
		{
			int dx = Math.abs(from.getX() - to.getX());
			int dy = Math.abs(from.getY() - to.getY());
			
			if ((from!=to))// && ((dx==0)||(dy==0)||(dx==dy)))
			{
				next = new NewTrack(from,to);
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
			
				
					for (int b=0; b<track.blocksIntersected.size()-1;b++)
					{
						Block from = track.blocksIntersected.get(b);
						Block to = track.blocksIntersected.get(b+1);
					
						if (city.hasTrack(from, to))
						{
							try
							{
								city.getWallet().addMoney(-track.getCost(from,to));
								simulation.getCity().removeTrack(from, to);
							}
							catch (Exception e)
							{
								System.out.println(e.getMessage());
							}
						}
						else
						{
							city.getWallet().addMoney(-track.getCost(from,to));
							simulation.getCity().createTrack(from, to);
						}
		
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
			
				for (int b=0; b<next.blocksIntersected.size()-1; b++)
				{
					Block from = next.blocksIntersected.get(b);
					Block to = next.blocksIntersected.get(b+1);					
					
					if (city.hasTrack(from, to))
					{
						Line line = new Line(from.getTrackNode(),to.getTrackNode());
						drawLine(line,1f,0f,0f,2f,false);
					}
					else
					{
						
						Point midpoint = new Point(
								from.getTrackNode().x+((to.getTrackNode().x - from.getTrackNode().x)*0.5),
								from.getTrackNode().y+((to.getTrackNode().y - from.getTrackNode().y)*0.5));
						
						
						
						Line line = new Line(from.getTrackNode(),midpoint);
						
						if (from.isBuilt())
						{
							drawLine(line,0f,0f,1f,2f,false);
						}
						else
						{
							drawLine(line,0f,0f,0f,2f,false);
						}
						
						line = new Line(midpoint,to.getTrackNode());
						
						if (to.isBuilt())
						{
							drawLine(line,0f,0f,1f,2f,false);
						}
						else
						{
							drawLine(line,0f,0f,0f,2f,false);
						}
		
					}

				}
				
				
			for (Block block : next.blocksIntersected)
			{
				if (block.isBuilt())
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
				
		List<Block> blocksIntersected = new ArrayList<Block> ();
				
		public NewTrack(Block from, Block to)
		{
			
			this.from = from;
			this.to = to;
			
			Line line = new Line(from.getTrackNode(),to.getTrackNode());

			int xd;
			
			if (from.getX()>to.getX())
			{
				xd=-1;
			}
			else
			{
				xd=1;
			}

			int yd;
			
			if (from.getY()>to.getY())
			{
				yd=-1;
			}
			else
			{
				yd=1;
			}
			
			blocksIntersected.add(from);
			
			for (int x=from.getX(); x!=to.getX()+xd; x+=xd)
			{
				for (int y=from.getY(); y!=to.getY()+yd; y+=yd)
				{
					Collection<Point> intersects = getIntersects(city.getBlock(x, y).getPolygon(),line);
					
					if (intersects.size()==2)
					{					
						blocksIntersected.add(city.getBlock(x, y));
						
						
					}
				}
			}
			
			blocksIntersected.add(to);

		}
		
		public int getCost(Block from, Block to)
		{
			double cost=0;
			
			Line line = new Line(from.getTrackNode(),to.getTrackNode());
			
			if (city.hasTrack(from, to))
			{
				cost += line.getLength()*removalCostPerKilometre;
			}
			else
			{
				int built=0;
				
				if (from.isBuilt())
				{
					built++;
				}
				if (to.isBuilt())
				{
					built ++;
				}
				
				cost += (built/2.0)*line.getLength()*buildCostPerBuiltUpKilometre;
				cost += (1.0-(built/2.0))*line.getLength()*buildCostPerKilometre;
			}
			
			return (int)Math.ceil(cost);
			
		}
		
		public int getCost()
		{
			int cost=0;
			
			for (int b=0; b<next.blocksIntersected.size()-1; b++)
			{
				Block from = next.blocksIntersected.get(b);
				Block to = next.blocksIntersected.get(b+1);
				
				cost += getCost(from,to);
			}
			
			return cost;
		}
	}


}
