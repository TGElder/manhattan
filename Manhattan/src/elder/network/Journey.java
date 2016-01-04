package elder.network;


public class Journey
{
	
	public final Node from;
	public final Node to;
	public final Vehicle vehicle;
	
	public Journey(Node from, Node to, Vehicle vehicle)
	{
		assert(from!=null);
		assert(to!=null);
		assert(vehicle!=null);
		this.from = from; 
		this.to = to;
		this.vehicle = vehicle;
	}
	

}
