package elder.manhattan.routines;

import java.util.ArrayList;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;

public class UpdateStations implements Routine
{

	@Override
	public String run(Simulation simulation)
	{
		
		
		for (int s=0; s<simulation.getCity().getRailwayNodes().size(); s++)
		{
			simulation.getCity().getRailwayNodes().get(s).setIndex(s);
		}
		
		return null;
	}

}
