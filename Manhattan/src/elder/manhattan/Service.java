package elder.manhattan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elder.network.Edge;

public class Service
{

	private String name;
	private Line line;
	
	private final List<Tubeway> sections = new ArrayList<Tubeway> ();
	
	public Service(String string)
	{
		setName(string);
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Line getLine()
	{
		return line;
	}
	public void setLine(Line line)
	{
		if (this.line!=null)
		{
			assert(this.line.getServices().contains(this));
			this.line.getServices().remove(this);
		}
		
		this.line = line;
		
		if (this.line!=null)
		{
			this.line.getServices().add(this);
		}
		
	}
	
	public String toString()
	{
		return name;
	}
	
	public void link(City city, Station a, Station b, Tube [] tubes, Tube [] reverse, double length)
	{
		Platform platformA = a.getPlatform(this);
		
		if (platformA==null)
		{
			platformA = a.addPlatform(this);
		}
		
		Platform platformB = b.getPlatform(this);
		
		if (platformB==null)
		{
			platformB = b.addPlatform(this);
		}

		Tubeway ab = new Tubeway(platformA,platformB,2,tubes,length);
		Tubeway ba = new Tubeway(platformB,platformA,2,reverse,length);
		
		platformA.addEdge(ab);
		platformB.addEdge(ba);
		
		ab.setReverse(ba);
		ba.setReverse(ab);
		
		sections.add(ab);
		sections.add(ba);
	}
	
	public void unlink(City city, Platform a, Platform b) throws Exception
	{
		Edge edge = a.getEdge(b);
		
		if (edge!=null)
		{
			Tubeway tubeway = (Tubeway)edge;
			Tubeway reverseTubeway = ((Tubeway)edge).getReverse();
			
			if (sections.contains(tubeway))
			{					
				assert(sections.contains(reverseTubeway));
				sections.remove(tubeway);
				sections.remove(reverseTubeway);
				
				a.removeEdge(tubeway);
				b.removeEdge(reverseTubeway);
				
				if (a.getEdges().size()==1)
				{
					a.getStation().removePlatform(this);
				}
				
				if (b.getEdges().size()==1)
				{
					b.getStation().removePlatform(this);
				}
				
				return;
			}
		}
		
		throw new Exception("These platforms are not linked by this service.");

	}
	
	public void unlink(City city, Station a, Station b) throws Exception
	{
		Platform platformA = a.getPlatform(this);
		Platform platformB = b.getPlatform(this);
		
		if (platformA!=null||platformB!=null)
		{
		
			unlink(city,platformA,platformB);
			return;
		}
		
		throw new Exception("This service does not serve both of these stations.");

	}
	
	public List<Tubeway> getSections()
	{
		return sections;
	}
	
	
}
