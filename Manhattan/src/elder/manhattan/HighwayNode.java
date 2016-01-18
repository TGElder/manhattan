package elder.manhattan;

public class HighwayNode extends IndexNode
{
	private final Block centre;
	private Block [] members;
	
	public HighwayNode(Block centre, Integer integer)
	{
		super(centre.getRoadNode().x, centre.getRoadNode().y, integer);
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
