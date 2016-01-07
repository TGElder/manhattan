package elder.manhattan;

import elder.network.SpeedEdge;

public class Tube extends SpeedEdge
{
	
	private Tube reverse;

	public Tube(Block from, Block to, double speed)
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

	public Tube getReverse()
	{
		return reverse;
	}

	public void setReverse(Tube reverse)
	{
		this.reverse = reverse;
	}
	
	public String toString()
	{
		return super.toString();
	}

	
}
