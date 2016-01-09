package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.Tube;
import elder.manhattan.Tubeway;

public class CreateTestTubes implements Routine
{

	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
		
		int xIncrement = city.getWidth()/32;
		int yIncrement = city.getHeight()/32;
		
		for (int x=0; x<(city.getHeight()/xIncrement); x++)
		{
			for (int y=0; y<(city.getHeight()/yIncrement); y++)
			{
				city.createStation(city.getBlock(x*xIncrement, y*yIncrement));
			}
		}
		
		List<Tube> tubes = new ArrayList<Tube> ();
		List<Tube> reverseTubes = new ArrayList<Tube> ();
		int length=0;
		
		for (int x=0; x<(city.getHeight()/xIncrement); x++)
		{
			int x2 = x*xIncrement;
			
			Station A;
			if (city.getBlock(x2, 0).hasStation())
			{
				A = city.createPlatform(city.getBlock(x2, 0));
			}
			else
			{
				A = null;
			}
			
			
			tubes.clear();
			reverseTubes.clear();
			length=0;
			
			for (int y=0; y<(city.getHeight()-1); y++)
			{
				Block a = city.getBlock(x2, y);
				Block b = city.getBlock(x2, y+1);
				
				
				
				Tube ab = new Tube(a,b,10);
				a.addEdge(ab);
				Tube ba = new Tube(b,a,10);
				b.addEdge(ba);
				
				ab.setReverse(ba);
				ba.setReverse(ab);
				
				tubes.add(ab);
				length += ab.length;
				reverseTubes.add(ba);
				
				if (b.hasStation())
				{
					Station platformB = city.createPlatform(b);

					if (A!=null)
					{
						Station platformA = A;
						
						Tube [] ABarray = new Tube[tubes.size()];
						Tube [] BAarray = new Tube[tubes.size()];
						for (int t=0; t<tubes.size(); t++)
						{
							ABarray[t] = tubes.get(t);
							BAarray[t] = reverseTubes.get(t);
						}
						
						Tubeway AB = new Tubeway(platformA,platformB,10,ABarray);
						AB.length = length;
						Tubeway BA = new Tubeway(platformB,platformA,10,BAarray);
						BA.length = length;
							
						platformA.addEdge(AB);
						platformB.addEdge(BA);
						
						AB.setReverse(BA);
						BA.setReverse(AB);

					}
					
					A = platformB;
					tubes.clear();
					reverseTubes.clear();
					length = 0;
				}

			}
		}
		
		for (int y=0; y<(city.getHeight()/yIncrement); y++)
		{
			int y2 = y*yIncrement;
			
			Station A;
			if (city.getBlock(0, y2).hasStation())
			{
				A = city.createPlatform(city.getBlock(0, y2));
			}
			else
			{
				A = null;
			}
			
			
			tubes.clear();
			reverseTubes.clear();
			length=0;
			
			for (int x=0; x<(city.getWidth()-1); x++)
			{
				Block a = city.getBlock(x, y2);
				Block b = city.getBlock(x+1, y2);
				
				
				
				Tube ab = new Tube(a,b,10);
				a.addEdge(ab);
				Tube ba = new Tube(b,a,10);
				b.addEdge(ba);
				
				ab.setReverse(ba);
				ba.setReverse(ab);

				tubes.add(ab);
				length += ab.length;
				reverseTubes.add(ba);
				
				if (b.hasStation())
				{
					Station platformB = city.createPlatform(b);

					if (A!=null)
					{
						Station platformA = A;
												
						Tube [] ABarray = new Tube[tubes.size()];
						Tube [] BAarray = new Tube[tubes.size()];
						for (int t=0; t<tubes.size(); t++)
						{
							ABarray[t] = tubes.get(t);
							BAarray[t] = reverseTubes.get(t);
						}
						
						Tubeway AB = new Tubeway(platformA,platformB,10,ABarray);
						AB.length = length;
						Tubeway BA = new Tubeway(platformB,platformA,10,BAarray);
						BA.length = length;
							
						platformA.addEdge(AB);
						platformB.addEdge(BA);
						
						AB.setReverse(BA);
						BA.setReverse(AB);
					}
					
					A = platformB;
					tubes.clear();
					reverseTubes.clear();
					length = 0;
				}
				
			}
		}
		
		
		
		return null;
	}

}
