package elder.manhattan;

public class City
{
	
	private final int width;
	private final int height;
	
	private final Block [] blocks;
	
	public City(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		blocks = new Block[width*height];
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public Block [] getBlocks()
	{
		return blocks;
	}

}
