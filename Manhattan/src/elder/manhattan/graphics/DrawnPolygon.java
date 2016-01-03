package elder.manhattan.graphics;

import elder.geometry.Point;
import elder.geometry.Polygon;

public class DrawnPolygon extends Drawable
{
	Polygon polygon;
	float R;
	float G;
	float B;
	boolean fill;
	final Point min;
	final Point max;
	
	public DrawnPolygon(Polygon polygon, float R, float G, float B, boolean fill)
	{
		this.polygon = polygon;
		this.min = polygon.getMin();
		this.max = polygon.getMax();
		this.R = R;
		this.G = G;
		this.B = B;
		this.fill = fill;
	}

	@Override
	public void draw2(CityDrawer cityDrawer)
	{
		cityDrawer.setColor(R, G, B);
		cityDrawer.drawPolygon(polygon, fill);
	}

	@Override
	public Point getMin()
	{
		return min;
	}

	@Override
	public Point getMax()
	{
		return max;
	}
	
	
}
