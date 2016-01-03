package elder.manhattan.graphics;

import elder.geometry.Point;

public class DrawnText extends Drawable
{
	
	public String text;
	int size;
	Point centre;
	
	public DrawnText(String text, int size, Point centre)
	{
		this.text = text;
		this.size = size;
		this.centre = centre;
	}

	@Override
	public void draw2(CityDrawer cityDrawer)
	{
		cityDrawer.write(this);
	}

	@Override
	public Point getMin()
	{
		return null;
	}

	@Override
	public Point getMax()
	{
		return null;
	}

}
