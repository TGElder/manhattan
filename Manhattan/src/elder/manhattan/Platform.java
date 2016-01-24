package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

public class Platform extends IndexNode
{

	private final Station station;
	private final Service service;
	
	private final Railway down;
	private final Railway up;
	
	private final List<Railway> tubeways = new ArrayList<Railway> ();
	
	
	public Platform(Station station, Service service)
	{
		super(station.x, station.y, null);
		this.station = station;
		this.service = service;
		
		down = new Railway(station,this,service,1, new SingleEdge[] {});
		down.setDelay(0);
		up = new Railway(this,station,service,1, new SingleEdge[] {});
		up.setDelay(0);
		
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
