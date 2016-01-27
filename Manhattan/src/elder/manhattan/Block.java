package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.geometry.Point;
import elder.geometry.Polygon;
import elder.network.Node;

public class Block
{
	
	private final int cityX;
	private final int cityY;
		
	private boolean built=false;
	
	private final Polygon polygon;
	
	private final List<Commute> workers = new ArrayList<Commute> ();
	private final List<Commute> residents = new ArrayList<Commute> ();
	
	private Block[] borders;
	private Block[] neighbours;
	private Station[] stations = {};
	
	private Station station;
	
	private final IndexNode trackNode;
	private HighwayNode highwayNode;
	
	private Town town;
	
	public Block(int x, int y, int index, Polygon polygon, Point centre)
	{
		trackNode = new IndexNode(centre.x,centre.y,index);
		
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
	
	void setBorders(Block [] borders)
	{
		this.borders = borders;
	}
	
	public Block[] getBorders()
	{
		return borders;
	}
 
	public double getPopulation()
	{
		return workers.size()+residents.size();
	}

	public Station getStation()
	{
		return station;
	}
	
	public boolean hasStation()
	{
		return station!=null;
	}

	public void setStation(Station station)
	{
		this.station = station;
	}

	public Station[] getStations()
	{
		return stations;
	}
	
	public void setStations(Station[] stations)
	{
		this.stations = stations;
	}


	public IndexNode getTrackNode()
	{
		return trackNode;
	}
	
	public Point getCentre()
	{
		return trackNode;
	}
	
	public HighwayNode getHighwayNode()
	{
		return highwayNode;
	}

	public void setHighwayNode(HighwayNode node)
	{
		this.highwayNode = node;
	}

	public Town getTown()
	{
		return town;
	}

	public void setTown(Town town)
	{
		this.town = town;
	}
	
	
	
}
