package elder.network;


public class SpeedEdge extends Edge
{

	private double speed;
	
	public SpeedEdge(Node A, Node B, double speed)
	{
		super(A, B);
		this.setSpeed(speed);
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

}
