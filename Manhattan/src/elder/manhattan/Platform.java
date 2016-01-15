package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

public class Platform extends RailwayNode
{

	private final Station station;
	private final Service service;
	
	private final Tubeway down;
	private final Tubeway up;
	
	private final List<Tubeway> tubeways = new ArrayList<Tubeway> ();
	
	public Platform(Station station, Service service)
	{
		super(station.x, station.y);
		this.station = station;
		this.service = service;
		
		down = new Tubeway(station,this,1, new Tube[] {},1);
		up = new Tubeway(this,station,1, new Tube[] {},1);
		
		up.setReverse(down);
		down.setReverse(up);
		
		station.addEdge(down);
		this.addEdge(up);
	}
	
	public void remove() throws Exception
	{
		if (!tubeways.isEmpty())
		{
			throw new Exception("Cannot remove platform with links to other platforms.");
		}
		station.removeEdge(down);
		this.removeEdge(up);
	}

	public Station getStation()
	{
		return station;
	}

	public Service getService()
	{
		return service;
	}



}
