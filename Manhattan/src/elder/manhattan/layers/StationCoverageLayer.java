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
	private final double threshold1;
	private final double threshold2;
	
	public StationCoverageLayer(Dijkstra roadDijkstra, double threshold1,double threshold2)
	{
		super("Station Coverage");
		this.roadDijkstra = roadDijkstra;
		this.threshold1 = threshold1;
		this.threshold2 = threshold2;
	}

	@Override
	public void draw(Simulation simulation)
	{

		for (Block block : simulation.getCity().getBlocks())
		{
			
			if (covered(simulation.getCity().getRailwayNodes(),block,threshold1))
			{
				drawPolygon(block.getPolygon(),1f,1f,0,0.5f,true);
			}
			else if (covered(simulation.getCity().getRailwayNodes(),block,threshold2))
			{
				drawPolygon(block.getPolygon(),1f,1f,0,0.25f,true);
			}
				
		}
		
	}
	
	private boolean covered(List<IndexNode> nodes, Block block, double threshold)
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
