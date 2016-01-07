package elder.manhattan.routines;

import java.util.ArrayList;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class UpdateStations implements Routine
{

	@Override
	public String run(Simulation simulation)
	{
		ArrayList<Integer> stations = new ArrayList<Integer> ();
		
		for (Block block : simulation.getCity().getBlocks())
		{
			
			stations.clear();
			
			
			if (block.hasStation())
			{
				stations.add(block.getStation());
			}
			
			for (Block neighbour : block.getNeighbours())
			{
				if (neighbour.hasStation())
				{
					stations.add(neighbour.getStation());
				}
			}
			
			block.setStations(new int[stations.size()]);
			
			for (int s=0; s<stations.size(); s++)
			{
				block.getStations()[s] = stations.get(s);
			}
			
			
		}
		
		return null;
	}

}
