package elder.manhattan.layers;

import elder.geometry.Line;
import elder.manhattan.Block;
import elder.manhattan.Simulation;


public class HighwayNodeLayer extends SimulationLayer
{
	
	public HighwayNodeLayer()
	{
		super("Highway Nodes");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		for (Block block : simulation.getCity().getBlocks())
		{
			drawLine(new Line(block.getTrackNode(),block.getHighwayNode()),1f,0f,0f,2f,false);
		}
		
	}

}
