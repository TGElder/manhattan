package elder.manhattan.graphics;

import elder.geometry.Point;
import elder.geometry.Polygon;

public abstract class Drawable
{
	public void draw(CityDrawer cityDrawer)
	{
		draw2(cityDrawer);
		
		Polygon border = cityDrawer.getBorder();
			
		
		if (getMin()==null||getMax()==null)
		{
			draw2(cityDrawer);
		}
		else if (overlap(border.get(0).a.x,border.get(2).a.x,getMin().x,getMax().x)&&overlap(border.get(0).a.y,border.get(2).a.y,getMin().y,getMax().y))
		{
			draw2(cityDrawer);

		}
		
	}
	
	private boolean overlap(double a, double b, double c, double d)
	{
		//System.out.println(a+", "+b+", "+c+", "+d);
		
		assert(a<=b);
		assert(c<=d);
		
		if((d<a)||(b<c))
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	
	
	public abstract void draw2(CityDrawer cityDrawer);

	
	public abstract Point getMin();
	public abstract Point getMax();
}
