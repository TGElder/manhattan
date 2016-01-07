package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Point;
import elder.geometry.Polygon;
import elder.network.Node;

public class Block extends Node
{
	
	private final int cityX;
	private final int cityY;
	
	private boolean built=false;
	
	private final Polygon polygon;
	
	private final List<Commute> workers = new ArrayList<Commute> ();
	private final List<Commute> residents = new ArrayList<Commute> ();
	
	private Block[] neighbours;
	private int[] stations = {};
	
	private int station=-1;
	
	
	public Block(int x, int y, Polygon polygon, Point centre)
	{
		super(centre.x,centre.y);
		this.cityX = x;
		this.cityY = y;
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
		return cityX;
	}
	
	public int getY()
	{
		return cityY;
	}
	
	@Override
	public String toString()
	{
		return "("+cityX+","+cityY+")";
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

	public int getStation()
	{
		return station;
	}
	
	public boolean hasStation()
	{
		return station>-1;
	}

	public void setStation(int station)
	{
		this.station = station;
	}

	public int[] getStations()
	{
		return stations;
	}
	
	public void setStations(int[] stations)
	{
		this.stations = stations;
	}
	

}
