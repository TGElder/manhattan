package elder.manhattan.routines;

import elder.manhattan.Block;
import elder.manhattan.Commute;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class AddImmigrants implements Routine
{

	private final int immigrants;
	
	public AddImmigrants(int immigrants)
	{
		this.immigrants = immigrants;
	}
	
	@Override
	public String run(Simulation simulation)
	{
				
		int workers=0;
		
		for (Block block : simulation.getCity().getBlocks())
		{
			if (block.getPopulation()<simulation.BLOCK_POPULATION_LIMIT)
			{
				workers += block.getWorkers().size();
			}
		}
				
		double chance = immigrants/(workers*1.0);
				
		int newWorkers=0;
		
		for (Block block : simulation.getCity().getBlocks())
		{
			int newBlockWorkers=0;
			
			
			for (int w=0; w<block.getWorkers().size(); w++)
			{
				if ((block.getPopulation()+newBlockWorkers)<simulation.BLOCK_POPULATION_LIMIT)
				{
					if (simulation.getRandom().nextDouble()<chance)
					{
						Commute commute = new Commute();
						commute.setOffice(block);
						simulation.getCity().getHomeless().add(commute);
						newWorkers++;
						newBlockWorkers++;
					}
				}
			}
			
		}
		
		return newWorkers+" immigrants added";
	}

}
