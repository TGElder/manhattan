package elder.manhattan.layers;


import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.IndexNode;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.routines.Dijkstra;


public class StationCoverageLayer extends SimulationLayer
{
	
	private final Dijkstra roadDijkstra;
	private final double threshold;
	
	public StationCoverageLayer(Dijkstra roadDijkstra, double threshold)
	{
		super("Station Coverage");
		this.roadDijkstra = roadDijkstra;
		this.threshold = threshold;
	}

	@Override
	public void draw(Simulation simulation)
	{

		for (Block block : simulation.getCity().getBlocks())
		{
			if (covered(simulation.getCity().getRailwayNodes(),block))
			{
				drawPolygon(block.getPolygon(),1f,1f,0,0.25f,true);
			}
		}
		
	}
	
	private boolean covered(List<IndexNode> nodes, Block block)
	{
		for (IndexNode station : nodes)
		{
			if (station instanceof Station)
			{
				if (roadDijkstra.getDistances()[block.getHighwayNode().getIndex()][((Station) station).getBlock().getHighwayNode().getIndex()] <= threshold)
				{
					return true;
				}
			}
		}
		return false;
	}

}
