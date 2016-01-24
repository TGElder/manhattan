package elder.manhattan;

import java.util.Collection;
import java.util.HashSet;

import elder.geometry.Point;

public class Town
{
	
	private final String name;
	private final HashSet<Block> blocks = new HashSet<Block> ();
	
	private Town parent;
	private final Collection<Town> children = new HashSet<Town> ();
	private final Collection<Town> neighbours = new HashSet<Town> ();
	
	private int population;
	
	private Point centre;
	
	private Town city;

	public Town(String name)
	{
		this.name = name;
		setCity(this);
	}

	public String getName()
	{
		return name;
	}

	public HashSet<Block> getBlocks()
	{
		return blocks;
	}

	public Town getParent()
	{
		return parent;
	}

	public void setParent(Town parent)
	{
		this.parent = parent;
		
		setCity(parent.getCity());
	}

	public Collection<Town> getChildren()
	{
		return children;
	}

	public Collection<Town> getNeighbours()
	{
		return neighbours;
	}

	public int getPopulation()
	{
		return population;
	}

	public Point getCentre()
	{
		return centre;
	}

	public void setCentre(Point centre)
	{
		this.centre = centre;
	}
	
	public int updatePopulation()
	{
		population = 0;
		
		for (Block block : getAllBlocks())
		{
			population += block.getPopulation();
		}
		
		return population;
	}
	
	public Collection<Block> getAllBlocks()
	{
		Collection<Block> out = new HashSet<Block>(getBlocks());
		
		for (Town child : getChildren())
		{
			out.addAll(child.getBlocks());
		}
		
		return out;
	}
	
	public Point updateCentre()
	{
		double x=0;
		double y=0;
		double p=0;

		for (Block block : getAllBlocks())
		{
			x += block.getCentre().x * block.getPopulation();
			y += block.getCentre().y * block.getPopulation();
			p += block.getPopulation();
		}
		
		centre = new Point(x/p,y/p);
		
		return centre;
	}

	public void addChild(Town town)
	{
		children.add(town);
	}

	public void addNeighbour(Town neighbour)
	{
		neighbours.add(neighbour);
	}

	
	public Town getCity()
	{
		return city;
	}
	
	public void setCity(Town city)
	{
		this.city = city;
		
		for (Town child : children)
		{
			child.setCity(city);
		}
	}
	
	
}
