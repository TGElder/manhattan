package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.Simulation;
import elder.network.Edge;


public class RoadLayer extends SimulationLayer
{
	
	public RoadLayer()
	{
		super("Roads");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		
		
		for (Block block : simulation.getCity().getBlocks())
		{
			for (Edge edge : block.getRoadNode().getEdges())
			{
				drawLine(edge,0f,0f,0f,3f,false);
			}

		}
		
	}

}
