package elder.manhattan.routines;

import java.util.Collection;
import java.util.HashSet;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Town;

public class GrowTowns implements Routine
{

	@Override
	public String run(Simulation simulation)
	{
	
		for (Block block : simulation.getCity().getBlocks())
		{
			if (block.getPopulation()>0)
			{
				Town town = block.getTown();
				
				Collection<Town> neighbours = new HashSet<Town> ();
				
				for (Block neighbour : block.getNeighbours())
				{
					if (neighbour.getPopulation()>0)
					{
						neighbours.add(neighbour.getTown());
					}
				}
			
				int townPopulation = town.getCity().getPopulation();
				
				for (Town neighbour : neighbours)
				{
					
					if (!town.getNeighbours().contains(neighbour))
					{
						town.addNeighbour(neighbour);
						neighbour.addNeighbour(town);
						
						if (town.getCity()!=neighbour.getCity())
						{
						
							int neighbourPopulation = neighbour.getCity().getPopulation();
							
							if (townPopulation>neighbourPopulation)
							{
								absorb(town,neighbour.getCity());
	
							}
							else
							{
	
								absorb(neighbour,town.getCity());
								
								townPopulation = town.getCity().getPopulation();
	
							}
							
						}
						
					}
				}
				
			}
			
		}
		
		
		
		
		return null;
	}
	
	private void absorb(Town a, Town b)
	{
		Town focus = a;
		
		int bPopulation = b.getPopulation();
		
		while (focus.getPopulation()<bPopulation)
		{
	
			focus = focus.getParent();
			
		}
		
		System.out.println(focus+" ("+focus.getPopulation()+") absorbs "+b+" ("+bPopulation+")");
		
		focus.addChild(b);
		b.setParent(focus);
	}

}
