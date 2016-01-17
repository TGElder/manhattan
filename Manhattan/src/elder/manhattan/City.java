package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Point;
import elder.geometry.Polygon;
import elder.network.Edge;

public class City
{
	
	private final int width;
	private final int height;
	private final double scale;
	
	private final Block [] blocks;
	
	private final List<Commute> homeless = new ArrayList<Commute> ();
	private final List<Commute> unemployed = new ArrayList<Commute> ();
	
	private static int [] neighbourXs = { 0,-1, 0, 1,-2,-1,1,2,-1,0,1,0};
	private static int [] neighbourYs = {-2,-1,-1,-1, 0, 0,0,0, 1,1,1,2};
	
	private final List<RailwayNode> railwayNodes = new ArrayList<RailwayNode> ();
	
	private final List<Line> lines = new ArrayList<Line> ();
	
	public City(int width, int height, double scale)
	{
		this.width = width;
		this.height = height;
		this.scale = scale;
		
		blocks = new Block[width*height];
		
		for (int x=0;x<width;x++)
		{
			for (int y=0;y<height; y++)
			{
				Polygon polygon = new Polygon();
				polygon.add(new Point(x*scale,y*scale));
				polygon.add(new Point((x+1)*scale,y*scale));
				polygon.add(new Point((x+1)*scale,(y+1)*scale));
				polygon.add(new Point(x*scale,(y+1)*scale));
				
				setBlock(x,y,new Block(x,y,(y*width)+x,polygon,new Point(scale*(x+0.5),scale*(y+0.5))));
				
			}
		}
		
		for (int x=0;x<width;x++)
		{
			for (int y=0;y<height; y++)
			{
				ArrayList<Block> neighbours1 = new ArrayList<Block> ();
				
				for (int n=0; n<neighbourXs.length; n++)
				{
					int nx = x+neighbourXs[n];
					int ny = y+neighbourYs[n];
					
					if (nx>0 && nx<width && ny>0 && ny<height)
					{
						neighbours1.add(getBlock(nx,ny));
					}
				}
				
				Block[] neighbours2 = new Block[neighbours1.size()];
				
				for (int n=0; n<neighbours1.size(); n++)
				{
					neighbours2[n] = neighbours1.get(n);
				}
				
				getBlock(x,y).setNeighbours(neighbours2);
			}
		}
	}
	
	private void setBlock(int x, int y, Block block)
	{
		blocks[(y*width)+x] = block;
	}
	
	public Block getBlock(int x, int y)
	{
		return blocks[(y*width)+x];
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public double getScale()
	{
		return scale;
	}
	
	public Block [] getBlocks()
	{
		return blocks;
	}

	public List<Commute> getHomeless()
	{
		return homeless;
	}

	public List<Commute> getUnemployed()
	{
		return unemployed;
	}
	
	public int getPopulation()
	{
		int population = homeless.size();
		
		for (Block block : blocks)
		{
			population += block.getResidents().size();
		}
		
		return population;
	}
	
	public List<RailwayNode> getRailwayNodes()
	{
		return railwayNodes;
	}
	
	public void createTrack(Block from, Block to)
	{
		assert(!from.hasEdge(to));
		
		SingleEdge fromTo = new SingleEdge(from,to);
		SingleEdge toFrom = new SingleEdge(to,from);
		
		fromTo.setReverse(toFrom);
		toFrom.setReverse(fromTo);
		
		from.addEdge(fromTo);
		to.addEdge(toFrom);
		
	}
	
	public void removeTrack(Block from, Block to) throws Exception
	{		
		SingleEdge singleEdge = (SingleEdge)(from.getEdge(to));
		
		assert(singleEdge!=null);
		
		if (!hasService(singleEdge))
		{
			from.removeEdge(singleEdge);
			to.removeEdge(singleEdge.getReverse());
		}
		else
		{
			throw new Exception("Cannot remove track with service running over it.");
		}
		
		
	}
	
	public void toggleTrack(Block from, Block to) throws Exception
	{
		SingleEdge singleEdge = (SingleEdge)(from.getEdge(to));
		
		if (singleEdge==null)
		{
			createTrack(from,to);
		}
		else
		{
			removeTrack(from,to);
		}
	}
	
	public void createStation(Block block)
	{
		assert(!block.hasStation());
		
		if (!block.isBuilt())
		{
			block.setBuilt(true);
		}
		
		Station station = new Station(block);
		block.setStation(station);
		railwayNodes.add(station);
	}
	
	public void removeStation(Block block) throws Exception
	{
		assert(block.hasStation());
		
		Station station = block.getStation();
		if (station.getEdges().isEmpty())
		{
			assert(railwayNodes.contains(station));
			railwayNodes.remove(station);
			block.setStation(null);

		}
		else
		{
			throw new Exception("Cannot remove station with services calling at it.");

		}
		
	}
	
	public void toggleStation(Block block) throws Exception
	{
		if (!block.hasStation())
		{
			createStation(block);
		}
		else
		{
			removeStation(block);
		}
	}
	
	private boolean hasService(SingleEdge singleEdge)
	{
		for (RailwayNode node : getRailwayNodes())
		{
			for (Edge edge : node.getEdges())
			{
				RailwayEdge railwayEdge = (RailwayEdge)edge;
				for (SingleEdge other : railwayEdge.getEdges())
				{
					if (other.equals(singleEdge))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	

	public List<Line> getLines()
	{
		return lines;
	}


}
