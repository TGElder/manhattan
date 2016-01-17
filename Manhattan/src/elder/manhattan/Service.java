package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.network.Edge;

public class Service
{

	private String name;
	private Line line;
	
	private final List<Section> sections = new ArrayList<Section> ();
	
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
	
	public Platform getStart()
	{
		if (!sections.isEmpty())
		{
			return (Platform)(sections.get(0).a);
		}
		else
		{
			return null;
		}
	}
	
	public Platform getEnd()
	{
		if (!sections.isEmpty())
		{
			return (Platform)(sections.get(sections.size()-1).b);
		}
		else
		{
			return null;
		}
	}
	
	public String toString()
	{
		return name;
	}
	
	public void link(City city, Station a, Station b, List<SingleEdge> singleEdge) throws Exception
	{
		
		
		if (sections.isEmpty()||a==getStart().getStation()||a==getEnd().getStation()||b==getStart().getStation()||b==getEnd().getStation())
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

			Section ab = new Section(platformA,platformB,this,2,singleEdge.toArray(new SingleEdge[singleEdge.size()]));
			Section ba = ab.createReverse();
			
			platformA.addEdge(ab);
			platformB.addEdge(ba);
			
			ab.setReverse(ba);
			ba.setReverse(ab);
			
			if (sections.isEmpty())
			{
				sections.add(ab);
			}
			else if(a==getStart().getStation())
			{
				sections.add(0,ba);
			}
			else if (a==getEnd().getStation())
			{
				sections.add(ab);
			}
			else if (b==getStart().getStation())
			{
				sections.add(0,ab);
			}
			else if(b==getEnd().getStation())
			{
				sections.add(ba);
			}
			
			System.out.println(sections);
		}
		else
		{
			throw new Exception("Section must join onto existing section.");
		}
		
	}
	
	public void unlink(City city, Platform a, Platform b) throws Exception
	{
		Edge edge = a.getEdge(b);
		
		if (edge!=null)
		{
			Section section = (Section)edge;
			Section reverse = ((Section)edge).getReverse();

			Section ab;
			
			if (sections.contains(section))
			{
				ab = section;
				assert(!sections.contains(reverse));
			}
			else if (sections.contains(reverse))
			{
				ab = reverse;
				assert(!sections.contains(section));
			}
			else
			{
				ab=null;
				assert(false);
			}
			
			int index = sections.indexOf(ab);
			
			if (index==0||index==sections.size()-1)
			{
				sections.remove(ab);
				
				System.out.println(sections);
				
				a.removeEdge(section);
				b.removeEdge(reverse);
				
				if (a.getEdges().size()==1)
				{
					a.getStation().removePlatform(this);
				}
				
				if (b.getEdges().size()==1)
				{
					b.getStation().removePlatform(this);
				}

			}
			else
			{
				throw new Exception("Can only remove sections at ends of service.");
			}
	
			
		}
		else
		{
			throw new Exception("These platforms are not linked by this service.");
		}
		
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
	
	public List<Section> getSections()
	{
		return sections;
	}
	
	
}
