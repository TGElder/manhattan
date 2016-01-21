package elder.manhattan.routines;

import java.awt.Color;
import java.util.ArrayList;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Line;
import elder.manhattan.MultiEdge;
import elder.manhattan.Routine;
import elder.manhattan.Service;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;
import elder.manhattan.Station;

public class CreateTestTubes implements Routine
{

	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
		
		int interval=5;
		
		Line line = new Line("Test", new Color(0f,0f,1f));
		
		// Build stations
		for (int x=0; x<city.getWidth(); x+=interval)
		{
			
			for (int y=0; y<city.getHeight(); y+=interval)
			{
				city.createStation(city.getBlock(x,y));
				
			}
		}
		
		// Build x track and services
		
		for (int x=0; x<city.getWidth(); x+=interval)
		{
			Service service = new Service("X"+x);
			service.setLine(line);
			
			for (int y=0; y<city.getHeight(); y+=interval)
			{
				if (y+interval<city.getHeight())
				{
					Block from = city.getBlock(x,y+interval);
					Block to = city.getBlock(x,y);
					SingleEdge track = city.createTrack(from, to);
					ArrayList<SingleEdge> trackList = new ArrayList<SingleEdge> ();
					trackList.add(track);
					try
					{
						service.link(city, from.getStation(), to.getStation(), trackList);
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
			}
		}
		
		// Build y track and services
		
		for (int y=0; y<city.getHeight(); y+=interval)
		{
			Service service = new Service("Y"+y);
			service.setLine(line);
			
			for (int x=0; x<city.getWidth(); x+=interval)
			{
				if (x+interval<city.getWidth())
				{
					Block from = city.getBlock(x,y);
					Block to = city.getBlock(x+interval,y);
					SingleEdge track = city.createTrack(from, to);
					ArrayList<SingleEdge> trackList = new ArrayList<SingleEdge> ();
					trackList.add(track);
					try
					{
						service.link(city, from.getStation(), to.getStation(), trackList);
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
			}
		}
		
		
		city.getLines().add(line);
		
		

		
		
		
		return null;
	}

}
