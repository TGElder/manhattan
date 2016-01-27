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

		int [][] covered = new int[simulation.getCity().getWidth()][simulation.getCity().getHeight()]; 
		
		for (Block block : simulation.getCity().getBlocks())
		{
			
			if (covered(simulation.getCity().getRailwayNodes(),block,threshold1))
			{
				covered[block.getX()][block.getY()] = 0;
				//drawPolygon(block.getPolygon(),1f,1f,0,0.5f,true);
			}
			else if (covered(simulation.getCity().getRailwayNodes(),block,threshold2))
			{
				covered[block.getX()][block.getY()] = 1;
				//drawPolygon(block.getPolygon(),1f,1f,0,0.25f,true);
			}
			else
			{
				covered[block.getX()][block.getY()] = 2;
			}
				
		}
		
		
		for (Block block : simulation.getCity().getBlocks())
		{	
			int blockCovered = covered[block.getX()][block.getY()];
			
			for (int b=0; b<block.getBorders().length; b++)
			{
				Block border = block.getBorders()[b];
				
				if (border!=null)
				{
					int borderCovered = covered[border.getX()][border.getY()];
					
					if (blockCovered==0&&borderCovered==1)
					{
						drawLine(block.getPolygon().get(b),1f,1f,1f,2f,false);
					}
					else if (blockCovered==1&&borderCovered==2)
					{
						drawLine(block.getPolygon().get(b),0f,0f,0f,2f,false);
					}
					
				}
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
