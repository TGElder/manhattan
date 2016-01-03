package elder.manhattan.graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import elder.geometry.Line;
import elder.geometry.Point;
import elder.geometry.Polygon;

public abstract class CityDrawerLayer
{
	
	protected List<Drawable> next = Collections.emptyList();
	protected List<Drawable> current = Collections.emptyList();
	
	protected boolean display=true;
	
	protected static Random random = new Random();

	private String name;
	
	public CityDrawerLayer(String name)
	{
		this.name = name;
	}
	
	public void draw(CityDrawer cityDrawer)
	{
		if (display)
		{
			for (Drawable drawable : current)
			{
				drawable.draw(cityDrawer);
			}
			
		}
	}
	
	protected void drawPolygon(Polygon polygon, float R, float G, float B, boolean fill)
	{
		
		next.add(new DrawnPolygon(polygon,R,G,B,fill));
	}
	
	
	protected void drawLine(Line line, float R, float G, float B, float thickness, boolean scaled)
	{
		
		next.add(new DrawnLine(line,R,G,B,thickness,scaled));
	}
	
	protected void drawPoint(Point point, float R, float G, float B, float size)
	{
		
		next.add(new DrawnPoint(point,R,G,B,size));
	}
	
	protected void drawText(String text, int size, Point centre)
	{
		next.add(new DrawnText(text,size,centre));
	}
	
	protected void draw(Drawable drawable)
	{
		next.add(drawable);
	}
	
	public void enable()
	{
		display=true;
	}
	
	public void disable()
	{
		display=false;
	}
	
	public boolean enabled()
	{
		return display;
	}
	
	public abstract void draw();
	
	public void refresh()
	{
		if (enabled())
		{
			next = new ArrayList<Drawable> ();
	
			
			draw();
			
			SwingUtilities.invokeLater
			(
					new Runnable()
					{
						@Override
						public void run()
						{
							current = next;
						}
					}
			);
		}
		
	}
		

	
	public String toString()
	{
		return name;
	}
	
	
}
