package elder.manhattan.layers;

import java.util.HashSet;

import elder.manhattan.Block;
import elder.manhattan.Simulation;
import elder.manhattan.Station;


public class StationCoverageLayer extends SimulationLayer
{
	
	public StationCoverageLayer()
	{
		super("Station Coverage");
	}

	@Override
	public void draw(Simulation simulation)
	{
		
		HashSet<Block> coverage = new HashSet<Block> ();
	
		
		for (Station station : simulation.getCity().getStations())
		{
			coverage.add(station.getBlock());
			
			for (Block block : station.getBlock().getNeighbours())
			{
				if (block.isBuilt())
				{
					coverage.add(block);
				}
			}
			
		}
		
		for (Block block : coverage)
		{
			
			drawPolygon(block.getPolygon(),1f,1f,0,0.5f,true);
		}
		
	}

}
