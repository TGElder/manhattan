package elder.manhattan.layers;

import elder.manhattan.Simulation;
import elder.manhattan.Station;


public class StationLayer extends SimulationLayer
{
	
	public StationLayer()
	{
		super("Stations");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		for (Station station : simulation.getCity().getStations())
		{
			if (station.getBlock().getStation()==station)
			{
				drawPoint(station,0f,0f,0f,6f);
				drawPoint(station,1f,1f,1f,4f);
			}
		}
		
	}

}
