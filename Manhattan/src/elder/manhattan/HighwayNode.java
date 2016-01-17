package elder.manhattan;

public class HighwayNode extends IndexNode
{
	private final Block centre;
	private Block [] members;
	
	public HighwayNode(double x, double y, Integer integer, Block centre)
	{
		super(x, y, integer);
		this.centre = centre;
	}

	public Block getCentre()
	{
		return centre;
	}

	public Block [] getMembers()
	{
		return members;
	}

	public void setMembers(Block [] members)
	{
		this.members = members;
	}


}
