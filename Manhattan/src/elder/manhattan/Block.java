package elder.manhattan;

import elder.geometry.Polygon;

public class Block
{
	
	private boolean built=false;
	
	private final Polygon polygon;
	
	public Block(Polygon polygon)
	{
		this.polygon = polygon;
	}

	public boolean isBuilt()
	{
		return built;
	}

	public void setBuilt(boolean built)
	{
		this.built = built;
	}

	public Polygon getPolygon()
	{
		return polygon;
	}
	
	

}
