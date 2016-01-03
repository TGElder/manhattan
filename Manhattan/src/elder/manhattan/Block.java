package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Polygon;

public class Block
{
	
	private final int x;
	private final int y;
	
	private boolean built=false;
	
	private final Polygon polygon;
	
	private final List<Commute> workers = new ArrayList<Commute> ();
	private final List<Commute> residents = new ArrayList<Commute> ();
	
	private Block[] neighbours;
	
	public Block(int x, int y, Polygon polygon)
	{
		this.x = x;
		this.y = y;
		this.polygon = polygon;
	}

	public boolean isBuilt()
	{
		return built;
	}

	public void setBuilt(boolean built)
	{
		this.built = built;
	}

	public Polygon getPolygon()
	{
		return polygon;
	}

	public List<Commute> getResidents()
	{
		return residents;
	}
	
	public List<Commute> getWorkers()
	{
		return workers;
	}
	
	public void addWorker(Commute worker)
	{
		workers.add(worker);
		assert(getPopulation()<=1000);
	}
	
	public void addResident(Commute resident)
	{
		residents.add(resident);
		assert(getPopulation()<=1000);
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	@Override
	public String toString()
	{
		return "("+x+","+y+")";
	}

	public Block[] getNeighbours()
	{
		return neighbours;
	}

	void setNeighbours(Block[] neighbours)
	{
		this.neighbours = neighbours;
	}

	public double getPopulation()
	{
		return workers.size()+residents.size();
	}
	

}
