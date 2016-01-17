package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

public class Platform extends RailwayNode
{

	private final Station station;
	private final Service service;
	
	private final RailwayEdge down;
	private final RailwayEdge up;
	
	private final List<RailwayEdge> tubeways = new ArrayList<RailwayEdge> ();
	
	
	public Platform(Station station, Service service)
	{
		super(station.x, station.y);
		this.station = station;
		this.service = service;
		
		down = new RailwayEdge(station,this,service,1, new SingleEdge[] {});
		down.setDelay(1);
		up = new RailwayEdge(this,station,service,1, new SingleEdge[] {});
		up.setDelay(1);
		
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
