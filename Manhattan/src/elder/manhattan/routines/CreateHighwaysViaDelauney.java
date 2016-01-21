package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import elder.geometry.DelaunayDiagrammer;
import elder.geometry.Point;
import elder.manhattan.Highway;
import elder.manhattan.HighwayNode;
import elder.manhattan.IndexNode;
import elder.manhattan.Pathfinder;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;

public class CreateHighwaysViaDelauney implements Routine
{

	private int trafficBias;
	
	public CreateHighwaysViaDelauney(int trafficBias)
	{
		this.trafficBias = trafficBias;
	}
	
	@Override
	public String run(Simulation simulation)
	{
		Pathfinder pathfinder = new Pathfinder(simulation.getCity());
		pathfinder.setTrafficBias(true);
		
		List<HighwayNode> nodes = new ArrayList<HighwayNode>(simulation.getCity().getHighwayNodes());
		
		Collections.shuffle(nodes);
		
		List<Collection<Point>> delauney = DelaunayDiagrammer.draw(nodes);
		

		for (int n=0; n<nodes.size(); n++)
		{
			HighwayNode a = nodes.get(n);
			
			for (Point neighbour : delauney.get(n))
			{
				HighwayNode b = (HighwayNode)neighbour;
				
				if (nodes.indexOf(b)>nodes.indexOf(a))
				{
					List<SingleEdge> path = pathfinder.findPath(a.getCentre().getRoadNode(), b.getCentre().getRoadNode(), simulation.getCity().getBlocks().length);
					
					for (SingleEdge singleEdge : path)
					{
						singleEdge.resetTraffic();
						singleEdge.addTraffic(trafficBias);
						singleEdge.getReverse().resetTraffic();
						singleEdge.getReverse().addTraffic(trafficBias);
					}
					
					Highway highway = new Highway(a,b,path.toArray(new SingleEdge[path.size()]),1);
					Highway reverse = new Highway(b,a,highway.computeReverse(),1);
					
					highway.setReverse(reverse);
					reverse.setReverse(highway);
					
					a.addEdge(highway);
					b.addEdge(reverse);

				}
				
				
			}
			
		}
		
		
		return null;
	}

}
