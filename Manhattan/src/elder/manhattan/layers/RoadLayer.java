package elder.manhattan.layers;

import elder.manhattan.Block;
import elder.manhattan.MultiEdge;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;
import elder.manhattan.routines.PlaceTraffic;
import elder.network.Edge;


public class RoadLayer extends SimulationLayer
{
	
	private final PlaceTraffic traffic;
	
	public RoadLayer(PlaceTraffic traffic)
	{
		super("Roads");
		this.traffic = traffic;
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		
		
		for (Block block : simulation.getCity().getBlocks())
		{
			for (Edge multiEdge : block.getHighwayNode().getEdges())
			{
				for (Edge edge: ((MultiEdge)multiEdge).getEdges())
				{
					SingleEdge singleEdge = (SingleEdge) edge;
					
					if (traffic.getMaxTraffic()>0)
					{
						if (singleEdge.getTraffic()>0)
						{
							float width = 1 + (singleEdge.getTraffic()*9f)/(traffic.getMaxTraffic()*1f);
							drawLine(edge,0f,0f,0f,width,false);
						}
					}
				}
				
				
			}

		}
		
	}

}
