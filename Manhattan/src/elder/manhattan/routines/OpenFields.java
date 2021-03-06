package elder.manhattan.routines;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class OpenFields implements Routine
{
	
	private final double openProbability;
	
	public OpenFields(double openProbability)
	{
		this.openProbability = openProbability;
	}
	
	@Override
	public String run(Simulation simulation)
	{
		
		for (Block block : simulation.getCity().getBlocks())
		{
			if (!block.isBuilt())
			{

				if (block.hasStation()&&!block.getStation().getPlatforms().isEmpty())
				{
					block.setBuilt(true);
				}
				else
				{
					double score=0.0;
				
					for (Block neighbour : block.getNeighbours())
					{
						if (neighbour.isBuilt())
						{
							
							double occupancy = (neighbour.getPopulation())/(simulation.BLOCK_POPULATION_LIMIT*1.0);
							
							score += occupancy*(openProbability/13.0);
						}
					}
				
				
					if (simulation.getRandom().nextDouble()<score)
					{
						block.setBuilt(true);
					}
				
				}
			}
		}
		
		return null;
	}


}
