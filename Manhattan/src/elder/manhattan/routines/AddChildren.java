package elder.manhattan.routines;

import elder.manhattan.Block;
import elder.manhattan.Commute;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class AddChildren implements Routine
{

	private final int children;
	
	public AddChildren(int children)
	{
		this.children = children;
	}
	
	@Override
	public String run(Simulation simulation)
	{
				
		int residents=0;
		
		for (Block block : simulation.getCity().getBlocks())
		{
			if (block.getPopulation()<simulation.BLOCK_POPULATION_LIMIT)
			{
				residents += block.getResidents().size();
			}
		}
				
		double chance = children/(residents*1.0);
		
		int newResidents=0;
		
		for (Block block : simulation.getCity().getBlocks())
		{
			int newBlockResidents=0;

			for (int r=0; r<block.getResidents().size(); r++)
			{
				if ((block.getPopulation()+newBlockResidents)<simulation.BLOCK_POPULATION_LIMIT)
				{
					if (simulation.getRandom().nextDouble()<chance)
					{
						Commute commute = new Commute();
						commute.setHome(block);
						simulation.getCity().getUnemployed().add(commute);
						newResidents++;
						newBlockResidents++;
					}
				}
			}
		}
		
		return newResidents+" children added";
	}

}
