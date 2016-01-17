package elder.manhattan;



public class RailwayEdge extends MultiEdge
{
	
	private RailwayEdge reverse;
	private Service service;
		
	private double delay;	
	private double speed;

	public RailwayEdge(RailwayNode a, RailwayNode b, Service service, double speed, SingleEdge[] track)
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
	
	public RailwayEdge createReverse()
	{
		return new RailwayEdge((RailwayNode)b,(RailwayNode)a,service,speed,computeReverse());
	}
	
	public RailwayNode getFrom()
	{
		return (RailwayNode)a;
	}

	public RailwayNode getTo()
	{
		return (RailwayNode)b;
	}
	
	public RailwayEdge getReverse()
	{
		return reverse;
	}

	public void setReverse(RailwayEdge reverse)
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
