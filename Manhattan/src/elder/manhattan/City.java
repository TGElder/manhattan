package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import elder.geometry.Point;
import elder.geometry.Polygon;
import elder.network.Edge;
import elder.network.Node;

public class City
{
	
	private final int width;
	private final int height;
	private final double scale;
	
	private final Block [] blocks;
	
	private final List<Commute> homeless = new ArrayList<Commute> ();
	private final List<Commute> unemployed = new ArrayList<Commute> ();
	
	private static int [] borderXs = {0,1,0,-1};
	private static int [] borderYs = {-1,0,1,0};
	private static int [] neighbourXs = { 0,-1, 0, 1,-2,-1,1,2,-1,0,1,0};
	private static int [] neighbourYs = {-2,-1,-1,-1, 0, 0,0,0, 1,1,1,2};
	
	private final List<IndexNode> railwayNodes = new ArrayList<IndexNode> ();
	
	private final List<Line> lines = new ArrayList<Line> ();
	
	private List<HighwayNode> highwayNodes;
	
	private final Wallet wallet;
	
	
	public City(int width, int height, double scale, int money)
	{
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.wallet = new Wallet(money);
		
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
				ArrayList<Block> neighbours = new ArrayList<Block> ();
				
				for (int n=0; n<neighbourXs.length; n++)
				{
					int nx = x+neighbourXs[n];
					int ny = y+neighbourYs[n];
					
					if (nx>=0 && nx<width && ny>=0 && ny<height)
					{
						neighbours.add(getBlock(nx,ny));
					}
				}
				
				Block [] borders = new Block[4];
				
				for (int b=0; b<borderXs.length; b++)
				{
					int bx = x+borderXs[b];
					int by = y+borderYs[b];
					
					if (bx>=0 && bx<width && by>=0 && by<height)
					{
						borders[b] = getBlock(bx,by);
					}
					else
					{
						borders[b] = null;
					}
				}
	
				
				getBlock(x,y).setNeighbours(neighbours.toArray(new Block[neighbours.size()]));
				getBlock(x,y).setBorders(borders);
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
	
	public List<IndexNode> getRailwayNodes()
	{
		return railwayNodes;
	}
	
	public SingleEdge createTrack(Block from, Block to)
	{
		assert(!from.getTrackNode().hasEdge(to.getTrackNode()));
		
		SingleEdge fromTo = new SingleEdge(from.getTrackNode(),to.getTrackNode());
		SingleEdge toFrom = new SingleEdge(to.getTrackNode(),from.getTrackNode());
		
		fromTo.setReverse(toFrom);
		toFrom.setReverse(fromTo);
		
		from.getTrackNode().addEdge(fromTo);
		to.getTrackNode().addEdge(toFrom);
		
		return fromTo;
		
	}
	
	public void removeTrack(Block from, Block to) throws Exception
	{		
		SingleEdge singleEdge = (SingleEdge)(from.getTrackNode().getEdge(to.getTrackNode()));
		
		assert(singleEdge!=null);
		
		if (!hasService(singleEdge))
		{
			from.getTrackNode().removeEdge(singleEdge);
			to.getTrackNode().removeEdge(singleEdge.getReverse());
		}
		else
		{
			throw new Exception("Cannot remove track with service running over it.");
		}
		
		
	}
	
	public boolean hasTrack(Block from, Block to)
	{
		SingleEdge singleEdge = (SingleEdge)(from.getTrackNode().getEdge(to.getTrackNode()));
		
		if (singleEdge==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void createStation(Block block, String name)
	{
		assert(!block.hasStation());
		
		Station station = new Station(block,name);
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
	
	
	public Platform createPlatform(Station station, Service service)
	{
		Platform platform = station.addPlatform(service);
		railwayNodes.add(platform);
		return platform;
	}
	
	public Platform removePlatform(Station station, Service service) throws Exception
	{
		Platform platform = station.removePlatform(service);
		railwayNodes.remove(platform);
		return platform;
	}
	
	private boolean hasService(SingleEdge singleEdge)
	{
		for (IndexNode node : getRailwayNodes())
		{
			for (Edge edge : node.getEdges())
			{
				Railway railway = (Railway)edge;
				for (SingleEdge other : railway.getEdges())
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

	public List<HighwayNode> getHighwayNodes()
	{
		return highwayNodes;
	}

	public void setHighwayNodes(List<HighwayNode> highwayNodes)
	{
		this.highwayNodes = highwayNodes;
	}

	public Wallet getWallet()
	{
		return wallet;
	}




}
