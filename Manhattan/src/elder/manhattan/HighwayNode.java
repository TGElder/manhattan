package elder.manhattan;


public class HighwayNode extends IndexNode
{
	private Block [] members;
	
	public HighwayNode(double x, double y, Integer integer)
	{
		super(x, y, integer);
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
