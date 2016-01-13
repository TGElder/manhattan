package elder.manhattan.layers;
import java.util.HashMap;
import java.util.Map;

import elder.manhattan.Block;
import elder.manhattan.Simulation;
import elder.manhattan.graphics.Colour;


public class KatherineLayer extends SimulationLayer
{
	private Map<Block,Colour> blocks = new HashMap<Block,Colour> ();

	
	public KatherineLayer()
	{
		super("Katherine");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		for (Block block : simulation.getCity().getBlocks())
		{
			
			if (block.isBuilt())
			{
			
				Colour colour = blocks.get(block);
				
				if (colour==null)
				{
					colour = new Colour();
					blocks.put(block,colour);
				}
						
				drawPolygon(block.getPolygon(),colour.R,colour.G,colour.B,0.25f,true);
				
			}

		}
		
		
		
	}

}
