package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.Commute;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;


public class Populate implements Routine
{
	
	private final int population;
	
	public Populate(int population)
	{
		this.population = population;
	}
	
	
	@Override
	public String run(Simulation simulation)
	{
		List<Block> candidates = new ArrayList<Block> ();
		
		for (Block block : simulation.getCity().getBlocks())
		{
			if (block.isBuilt())
			{
				candidates.add(block);
			}
		}
		
		for (int c=0; c<population; c++)
		{
			Commute commute = new Commute();
			commute.setHome(candidates.get(simulation.getRandom().nextInt(candidates.size())));
			simulation.getCity().getUnemployed().add(commute);
		}
		
		return null;
	}
	
}
