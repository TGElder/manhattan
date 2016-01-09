package elder.manhattan.routines;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Tube;
import elder.network.Edge;

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
			int x2 = x*xIncrement;
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

			}
		}
		
		for (int y=0; y<(city.getHeight()/yIncrement); y++)
		{
			int y2 = y*yIncrement;
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

			}
		}
		
		for (int x=0; x<(city.getHeight()/xIncrement); x++)
		{
			for (int y=0; y<(city.getHeight()/yIncrement); y++)
			{
				city.createStation(city.getBlock(x*xIncrement, y*yIncrement));
			}
		}
		
		return null;
	}

}
