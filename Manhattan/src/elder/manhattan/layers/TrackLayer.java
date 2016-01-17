package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.Simulation;
import elder.network.Edge;


public class TrackLayer extends SimulationLayer
{
	
	public TrackLayer()
	{
		super("Track");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		
		
		for (Block block : simulation.getCity().getBlocks())
		{
			for (Edge edge : block.getTrackNode().getEdges())
			{
				drawLine(edge,0f,0f,0f,3f,false);
			}

		}
		
	}

}
