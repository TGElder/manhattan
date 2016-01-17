package elder.manhattan;

public class Railway extends TimeEdge
{
	
	private Service service;
		
	private double delay;	
	private double speed;

	public Railway(IndexNode a, IndexNode b, Service service, double speed, SingleEdge[] track)
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
	
	public Railway createReverse()
	{
		return new Railway((IndexNode)b,(IndexNode)a,service,speed,computeReverse());
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
