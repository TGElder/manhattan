package elder.manhattan;


import elder.network.SpeedEdge;

public class Section extends SpeedEdge
{
	
	
	private Section reverse;
	private Service service;
	private Track [] track;
	
	public final double length;
	
	private double delay;	

	public Section(RailwayNode a, RailwayNode b, Service service, double speed, Track[] track)
	{
		super(a, b, speed);
		this.track = track;
		this.service = service;
		
		double length=0;
		
		for (Track t : track)
		{
			length+=t.length;
		}
		
		this.length = length; 
	}
	
	public void setDelay(double delay)
	{
		this.delay = delay;
	}
	
	public double getTime()
	{
		return (length/getSpeed()) + delay;
	}
	
	public Section createReverse()
	{
		Track[] reverse = new Track[track.length];
		
		for (int t=0; t<track.length; t++)
		{
			reverse[t] = track[(track.length - 1) - t];
		}
		
		return new Section((RailwayNode)b,(RailwayNode)a,service,getSpeed(),reverse);
	}
	
	public Station getFrom()
	{
		return (Station)a;
	}

	public Station getTo()
	{
		return (Station)b;
	}
	
	public Section getReverse()
	{
		return reverse;
	}

	public void setReverse(Section reverse)
	{
		this.reverse = reverse;
	}

	public Track [] getTubes()
	{
		return track;
	}
	
	public Service getService()
	{
		return service;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return this==obj;
	}
	
	

}
