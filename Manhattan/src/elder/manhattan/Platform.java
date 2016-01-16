package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

public class Platform extends RailwayNode
{

	private final Station station;
	private final Service service;
	
	private final Section down;
	private final Section up;
	
	private final List<Section> tubeways = new ArrayList<Section> ();
	
	
	public Platform(Station station, Service service)
	{
		super(station.x, station.y);
		this.station = station;
		this.service = service;
		
		down = new Section(station,this,service,1, new Track[] {});
		down.setDelay(1);
		up = new Section(this,station,service,1, new Track[] {});
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
