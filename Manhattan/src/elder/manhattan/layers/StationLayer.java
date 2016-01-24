package elder.manhattan.layers;

import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.network.Node;


public class StationLayer extends SimulationLayer
{
	
	public StationLayer()
	{
		super("Stations");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		for (Node node : simulation.getCity().getRailwayNodes())
		{
			if (node instanceof Station)
			{
				Station station = (Station)node;
				
				if (station.getBlock().getStation()==station)
				{
					drawText(station.getName(),16,station);
					drawPoint(station,0f,0f,0f,6f);
					drawPoint(station,1f,1f,1f,4f);
				}
			}
			
			
		}
		
	}

}
