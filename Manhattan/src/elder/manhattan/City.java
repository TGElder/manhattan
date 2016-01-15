package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Point;
import elder.geometry.Polygon;
import elder.network.Node;

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
	
	public void createStation(Block block)
	{
		if (!block.hasStation())
		{
		
			if (!block.isBuilt())
			{
				block.setBuilt(true);
			}
			
			Station station = new Station(block);
			block.setStation(station);
			railwayNodes.add(station);
			
			
		}
	}
	
	public boolean deleteStation(Block block)
	{
		if (block.hasStation())
		{
			Station station = block.getStation();
			if (station.getEdges().isEmpty())
			{
				System.out.println("Removing station from "+block);
				assert(railwayNodes.contains(station));
				railwayNodes.remove(station);
				System.out.println(railwayNodes);
				block.setStation(null);

			}
			else
			{
				System.out.println("Can't remove station, service calling at station.");

				return false;
			}

		}
		
		return true;
	}
	

	public List<Line> getLines()
	{
		return lines;
	}


}
