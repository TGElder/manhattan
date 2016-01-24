package elder.manhattan.routines;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class OpenRandomFields implements Routine
{
	
	private final double openProbability;
	
	public OpenRandomFields(double openProbability)
	{
		this.openProbability = openProbability;
	}
	
	@Override
	public String run(Simulation simulation)
	{
		
		int blocks = (int)(simulation.getCity().getBlocks().length*openProbability);
		int opened=0;
		
		for (int b=0; b<blocks;b++)
		{
			int x = simulation.getRandom().nextInt(simulation.getCity().getWidth()/4)+ simulation.getRandom().nextInt(simulation.getCity().getWidth()/4) + simulation.getRandom().nextInt(simulation.getCity().getWidth()/4) + simulation.getRandom().nextInt(simulation.getCity().getWidth()/4);
			int y = simulation.getRandom().nextInt(simulation.getCity().getHeight()/4)+ simulation.getRandom().nextInt(simulation.getCity().getHeight()/4)+ simulation.getRandom().nextInt(simulation.getCity().getHeight()/4)+ simulation.getRandom().nextInt(simulation.getCity().getHeight()/4);
			
			Block open = simulation.getCity().getBlock(x,y);
			
			if (!open.isBuilt())
			{
				opened++;
				open.setBuilt(true);
			}
		}
		
		
		return "Opened "+opened+" fields out of "+blocks;
	}


}
