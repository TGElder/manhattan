package elder.manhattan.layers;

import elder.manhattan.Simulation;
import elder.network.Edge;
import elder.network.Node;


public class TubewayLayer extends SimulationLayer
{
	
	public TubewayLayer()
	{
		super("Tubeways");
	}

	@Override
	public void draw(Simulation simulation)
	{
				

		for (Node node : simulation.getCity().getRailwayNodes())
		{
			for (Edge edge : node.getEdges())
			{
				drawLine(edge,0f,0f,1f,3f,false);
			}

		}
	
		
	}

}
