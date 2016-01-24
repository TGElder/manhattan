package elder.manhattan.routines;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class UpdateTowns implements Routine
{
	

	@Override
	public String run(Simulation simulation)
	{
		for (Block block : simulation.getCity().getBlocks())
		{
			block.getTown().updatePopulation();
			block.getTown().updateCentre();
		}

		return null;
	}

}
