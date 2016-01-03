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
				if (block.getResidents().isEmpty()&&block.getWorkers().isEmpty())
				{
					drawPolygon(block.getPolygon(),0.5f,0.5f,0.5f,true);
				}
				else
				{
					drawPolygon(block.getPolygon(),0f,0f,0f,true);
				}
			}
			else
			{
				drawPolygon(block.getPolygon(),0f,1f,0f,true);
			}

		}
		
		
		
	}

}
