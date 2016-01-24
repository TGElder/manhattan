package elder.manhattan.layers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.Simulation;
import elder.manhattan.Town;

public class TownLayer extends SimulationLayer
{
		
	public TownLayer()
	{
		super("Towns");
	}

	@Override
	public void draw(Simulation simulation)
	{

		List<Town> open = new ArrayList<Town> ();
		
		for (Block block : simulation.getCity().getBlocks())
		{			
			if (block.getTown().getPopulation()>0)
			{
				open.add(block.getTown());
			}
		
		}
				
		open.sort(new Comparator<Town> ()
				{

					@Override
					public int compare(Town a, Town b)
					{
						int aPopulation = a.getPopulation();
						int bPopulation = b.getPopulation();

						if (aPopulation<bPopulation)
						{
							return 1;
						}
						else if (aPopulation>bPopulation)
						{
							return -1;
						}
						else
						{
							return 0;
						}
					}
			
				}
		);
		
	
		for (Town town : open)
		{
			Point centre = town.getCentre();
			
			int population = town.getPopulation();
			
			int size;
			
			if (population<100)
			{
				size = 14;
			}
			else if (population<1000)
			{
				size = 16;
			}
			else if (population<10000)
			{
				size = 18;
			}
			else if (population<100000)
			{
				size = 20;
			}
			else if (population<1000000)
			{
				size = 22;
			}
			else
			{
				size = 24;
			}
			
			drawText(town.getName(),size,centre);
		}
		
		
	}

}
