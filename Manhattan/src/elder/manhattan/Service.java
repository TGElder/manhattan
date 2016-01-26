package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.network.Edge;

public class Service
{

	private String name;
	private Line line;
	
	private final List<Railway> railways = new ArrayList<Railway> ();
	
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
		if (!railways.isEmpty())
		{
			return (Platform)(railways.get(0).a);
		}
		else
		{
			return null;
		}
	}
	
	public Platform getEnd()
	{
		if (!railways.isEmpty())
		{
			return (Platform)(railways.get(railways.size()-1).b);
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
		
		
		if (railways.isEmpty()||a==getStart().getStation()||a==getEnd().getStation()||b==getStart().getStation()||b==getEnd().getStation())
		{
			Platform platformA = a.getPlatform(this);
			
			if (platformA==null)
			{
				platformA = city.createPlatform(a,this);
			}
			
			Platform platformB = b.getPlatform(this);
			
			if (platformB==null)
			{
				platformB = city.createPlatform(b,this);
			}

			Railway ab = new Railway(platformA,platformB,this,2,singleEdge.toArray(new SingleEdge[singleEdge.size()]));
			Railway ba = ab.createReverse();
			
			platformA.addEdge(ab);
			platformB.addEdge(ba);
			
			ab.setReverse(ba);
			ba.setReverse(ab);
			
			if (railways.isEmpty())
			{
				railways.add(ab);
			}
			else if(a==getStart().getStation())
			{
				railways.add(0,ba);
			}
			else if (a==getEnd().getStation())
			{
				railways.add(ab);
			}
			else if (b==getStart().getStation())
			{
				railways.add(0,ab);
			}
			else if(b==getEnd().getStation())
			{
				railways.add(ba);
			}
			
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
			Railway railway = (Railway)edge;
			Railway reverse = (Railway)(railway.getReverse());

			Railway ab;
			
			if (railways.contains(railway))
			{
				ab = railway;
				assert(!railways.contains(reverse));
			}
			else if (railways.contains(reverse))
			{
				ab = reverse;
				assert(!railways.contains(railway));
			}
			else
			{
				ab=null;
				assert(false);
			}
			
			int index = railways.indexOf(ab);
			
			if (index==0||index==railways.size()-1)
			{
				railways.remove(ab);
				
				System.out.println(railways);
				
				a.removeEdge(railway);
				b.removeEdge(reverse);
				
				if (a.getEdges().size()==1)
				{
					city.removePlatform(a.getStation(),this);
				}
				
				if (b.getEdges().size()==1)
				{
					city.removePlatform(b.getStation(),this);
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
	
	public List<Railway> getSections()
	{
		return railways;
	}
	
	public void removeAllSections(City city)
	{
		List<Railway> railways = new ArrayList<Railway> (this.railways); 
		
		for (Railway railway : railways)
		{
			
			try
			{
				unlink(city, (Platform)railway.a, (Platform)railway.b);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
}
