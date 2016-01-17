package elder.manhattan;


import elder.network.Edge;
import elder.network.SpeedEdge;

public class Section extends MultiEdge
{
	
	
	private Section reverse;
	private Service service;
		
	private double delay;	
	private double speed;

	public Section(RailwayNode a, RailwayNode b, Service service, double speed, SingleEdge[] track)
	{
		super(a, b,track);
		this.service = service;
		this.speed = speed;
	}
	
	public void setDelay(double delay)
	{
		this.delay = delay;
	}
	
	public double getTime()
	{
		return (length/speed) + delay;
	}
	
	public Section createReverse()
	{
		return new Section((RailwayNode)b,(RailwayNode)a,service,speed,computeReverse());
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
