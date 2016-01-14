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
	
	public Station getPlatform(Station station)
	{
		return platforms.get(station);
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
	
	public boolean unlink(City city, Station from, Station to)
	{
		Edge edge = from.getEdge(to);
		Edge reverseEdge = to.getEdge(from);
		
		if (edge!=null)
		{
			Tubeway tubeway = (Tubeway)edge;
			Tubeway reverseTubeway = (Tubeway)reverseEdge;
			
			if (sections.contains(tubeway))
			{
				System.out.println("Unlinking "+tubeway);
				
				assert(sections.contains(reverseEdge));
				sections.remove(tubeway);
				sections.remove(reverseTubeway);
				
				from.removeEdge(tubeway);
				to.removeEdge(reverseTubeway);
				
				if (from.getEdges().size()==1)
				{
					city.deletePlatform(from);
					assert(platforms.containsKey(from.getBlock().getStation()));
					platforms.remove(from.getBlock().getStation());
				}
				
				if (to.getEdges().size()==1)
				{
					city.deletePlatform(to);
					assert(platforms.containsKey(to.getBlock().getStation()));
					platforms.remove(to.getBlock().getStation());
				}
				
				return true;
			}
			else
			{
				System.out.println("Cannot remove section, stations not connected by this service");
			}
		}
		else
		{
			System.out.println("Cannot remove section, stations not connected");
		}
		return false;
		
		
	}
	
	public List<Tubeway> getSections()
	{
		return sections;
	}
	
	
}
