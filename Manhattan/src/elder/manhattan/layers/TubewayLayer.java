package elder.manhattan.layers;

import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.network.Edge;


public class TubewayLayer extends SimulationLayer
{
	
	public TubewayLayer()
	{
		super("Tubeways");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		
		
		for (Station station : simulation.getCity().getStations())
		{
			for (Edge edge : station.getEdges())
			{
				drawLine(edge,0f,0f,1f,3f,false);
			}

		}
	
		
	}

}
