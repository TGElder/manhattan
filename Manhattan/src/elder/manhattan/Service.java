package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

import elder.network.Edge;

public class Service
{

	private String name;
	private Line line;
	
	private final List<RailwayEdge> railwayEdges = new ArrayList<RailwayEdge> ();
	
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
		if (!railwayEdges.isEmpty())
		{
			return (Platform)(railwayEdges.get(0).a);
		}
		else
		{
			return null;
		}
	}
	
	public Platform getEnd()
	{
		if (!railwayEdges.isEmpty())
		{
			return (Platform)(railwayEdges.get(railwayEdges.size()-1).b);
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
		
		
		if (railwayEdges.isEmpty()||a==getStart().getStation()||a==getEnd().getStation()||b==getStart().getStation()||b==getEnd().getStation())
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

			RailwayEdge ab = new RailwayEdge(platformA,platformB,this,2,singleEdge.toArray(new SingleEdge[singleEdge.size()]));
			RailwayEdge ba = ab.createReverse();
			
			platformA.addEdge(ab);
			platformB.addEdge(ba);
			
			ab.setReverse(ba);
			ba.setReverse(ab);
			
			if (railwayEdges.isEmpty())
			{
				railwayEdges.add(ab);
			}
			else if(a==getStart().getStation())
			{
				railwayEdges.add(0,ba);
			}
			else if (a==getEnd().getStation())
			{
				railwayEdges.add(ab);
			}
			else if (b==getStart().getStation())
			{
				railwayEdges.add(0,ab);
			}
			else if(b==getEnd().getStation())
			{
				railwayEdges.add(ba);
			}
			
			System.out.println(railwayEdges);
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
			RailwayEdge railwayEdge = (RailwayEdge)edge;
			RailwayEdge reverse = (RailwayEdge)(railwayEdge.getReverse());

			RailwayEdge ab;
			
			if (railwayEdges.contains(railwayEdge))
			{
				ab = railwayEdge;
				assert(!railwayEdges.contains(reverse));
			}
			else if (railwayEdges.contains(reverse))
			{
				ab = reverse;
				assert(!railwayEdges.contains(railwayEdge));
			}
			else
			{
				ab=null;
				assert(false);
			}
			
			int index = railwayEdges.indexOf(ab);
			
			if (index==0||index==railwayEdges.size()-1)
			{
				railwayEdges.remove(ab);
				
				System.out.println(railwayEdges);
				
				a.removeEdge(railwayEdge);
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
	
	public List<RailwayEdge> getSections()
	{
		return railwayEdges;
	}
	
	
}
