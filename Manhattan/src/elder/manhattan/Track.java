package elder.manhattan;

import elder.network.SpeedEdge;

public class Track extends SpeedEdge
{
	
	private Track reverse;
	
	int traffic=0;

	public Track(Block from, Block to, double speed)
	{
		super(from,to,speed);
	}

	public Block getFrom()
	{
		return (Block)a;
	}

	public Block getTo()
	{
		return (Block)b;
	}

	public Track getReverse()
	{
		return reverse;
	}

	public void setReverse(Track reverse)
	{
		this.reverse = reverse;
	}
	
	public String toString()
	{
		return super.toString();
	}

	public void addTraffic(int traffic)
	{
		this.traffic += traffic; 
	}
	
	public int getTraffic()
	{
		return traffic;
	}
	
	public void resetTraffic()
	{
		traffic=0;
	}
	
}
