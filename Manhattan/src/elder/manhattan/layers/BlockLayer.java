package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.Simulation;


public class BlockLayer extends SimulationLayer
{
	
	public BlockLayer()
	{
		super("Blocks");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		for (Block block : simulation.getCity().getBlocks())
		{
			
			if (block.isBuilt())
			{
				float occupancy = (float) ((block.getPopulation())/(simulation.BLOCK_POPULATION_LIMIT*1.0));
				occupancy *= 0.75;
				occupancy = 0.75f - occupancy;
				
				drawPolygon(block.getPolygon(),occupancy,occupancy,occupancy,1,true);
				
			}
			else if (!block.getResidents().isEmpty()&&!block.getWorkers().isEmpty())
			{
				drawPolygon(block.getPolygon(),1f,0f,0f,1,true);
			}
			else
			{
				drawPolygon(block.getPolygon(),0f,1f,0f,1,true);
			}

		}
		
		
		
	}

}
