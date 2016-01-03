package elder.manhattan.graphics;

import elder.geometry.Point;

public class DrawnPoint extends Drawable
{
	final Point point;
	final float R;
	final float G;
	final float B;
	final float size;
	
	public DrawnPoint(Point point, float R, float G, float B, float size)
	{
		this.point = point;
		this.R = R;
		this.G = G;
		this.B = B;
		this.size = size;
	}

	@Override
	public void draw2(CityDrawer cityDrawer)
	{
		cityDrawer.setColor(R, G, B);
		cityDrawer.drawPoint(point, size);
	}
	
	@Override
	public Point getMin()
	{
		return point;
	}
	
	@Override
	public Point getMax()
	{
		return point;
	}
}
