package elder.manhattan;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Line
{
	
	private String name;
	private Color color;
	private final List<Service> services = new ArrayList<Service> ();
	
	public Line(String name, Color color)
	{
		this.setName(name);
		this.setColor(color);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public String toString()
	{
		return name;
	}

	public List<Service> getServices()
	{
		return services;
	}

}
