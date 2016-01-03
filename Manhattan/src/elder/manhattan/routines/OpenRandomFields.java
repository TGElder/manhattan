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
		
		for (Block block : simulation.getCity().getBlocks())
		{
			if (simulation.getRandom().nextDouble()<openProbability)
			{
				block.setBuilt(true);
			}
		}
		
		return null;
	}


}
