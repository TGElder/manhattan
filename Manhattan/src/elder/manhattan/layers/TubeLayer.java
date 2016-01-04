package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.Simulation;
import elder.network.Edge;


public class TubeLayer extends SimulationLayer
{
	
	public TubeLayer()
	{
		super("Tubes");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		for (Block block : simulation.getCity().getBlocks())
		{
			for (Edge edge : block.getEdges())
			{
				drawLine(edge,0f,0f,1f,3f,false);
			}

		}
		
		
		
	}

}