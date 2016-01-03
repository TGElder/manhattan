package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Point;
import elder.geometry.Polygon;

public class City
{
	
	private final int width;
	private final int height;
	private final double scale;
	
	private final Block [] blocks;
	
	private final List<Commute> homeless = new ArrayList<Commute> ();
	private final List<Commute> unemployed = new ArrayList<Commute> ();
	
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
				
				setBlock(x,y,new Block(x,y,polygon));
				
			}
		}
		
		for (int x=0;x<width;x++)
		{
			for (int y=0;y<height; y++)
			{
				ArrayList<Block> neighbours1 = new ArrayList<Block> ();
								
				if (x-1>0)
				{
					neighbours1.add(getBlock(x-1,y));
				}
				if (x+1<width)
				{
					neighbours1.add(getBlock(x+1,y));
				}
				if (y-1>0)
				{
					neighbours1.add(getBlock(x,y-1));
				}
				if (y+1<height)
				{
					neighbours1.add(getBlock(x,y+1));
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

	

}
