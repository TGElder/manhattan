package elder.manhattan;

import elder.network.SpeedEdge;

public class Tubeway extends SpeedEdge
{
	
	
	private Tubeway reverse;
	private Tube [] tubes;
	
	public double length;

	public Tubeway(Station a, Station b, double speed, Tube[] tubes)
	{
		super(a, b, speed);
		this.tubes = tubes;
	}
	
	public Station getFrom()
	{
		return (Station)a;
	}

	public Station getTo()
	{
		return (Station)b;
	}
	
	public Tubeway getReverse()
	{
		return reverse;
	}

	public void setReverse(Tubeway reverse)
	{
		this.reverse = reverse;
	}

	public String toString()
	{
		String out = super.toString()+" ("+length+")";
		
		for (Tube tube : tubes)
		{
			out += ", "+tube;
		}
		return out;
	}

	public Tube [] getTubes()
	{
		return tubes;
	}

}