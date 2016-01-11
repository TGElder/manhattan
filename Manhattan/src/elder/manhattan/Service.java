package elder.manhattan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service
{

	private String name;
	private Line line;
	
	private final Map<Station,Station> platforms = new HashMap<Station,Station> ();
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
		Station platformA = platforms.get(a);
		
		if (platformA==null)
		{
			platformA = city.createPlatform(a.getBlock());
			platforms.put(a,platformA);
		}
		
		Station platformB = platforms.get(b);
		
		if (platformB==null)
		{
			platformB = city.createPlatform(b.getBlock());
			platforms.put(b,platformB);
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
	public List<Tubeway> getSections()
	{
		return sections;
	}
	
	
}
