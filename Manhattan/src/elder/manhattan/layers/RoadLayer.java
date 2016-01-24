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
	private final int trafficType;
	
	public RoadLayer(PlaceTraffic traffic, String name, int trafficType)
	{
		super(name);
		this.traffic = traffic;
		this.trafficType = trafficType;
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
						if (singleEdge.getTraffic(trafficType)>0)
						{
							float width = 1 + (singleEdge.getTraffic(trafficType)*9f)/(traffic.getMaxTraffic()*1f);
							drawLine(edge,1f,1f,1f,width,false);
						}
					}
				}
				
				
			}

		}
		
	}

}
