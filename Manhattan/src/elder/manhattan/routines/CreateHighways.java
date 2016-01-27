package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Highway;
import elder.manhattan.HighwayNode;
import elder.manhattan.IndexNode;
import elder.manhattan.MultiEdge;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;
import elder.network.Edge;
import elder.network.Node;

public class CreateHighways implements Routine
{

	private static int [] neighbourXs = {1,0};
	private static int [] neighbourYs = {0,1};

	@Override
	public String run(Simulation simulation)
	{
	
		City city = simulation.getCity();
		
		List<HighwayNode> nodes = new ArrayList<HighwayNode> ();
		
		HighwayNode matrix[][] = new HighwayNode[(city.getWidth()/2)][(city.getHeight()/2)];
		
		for (int x=1; x<city.getWidth(); x+=2)
		{
			for (int y=1; y<city.getHeight(); y+=2)
			{
				HighwayNode node = new HighwayNode(x*city.getScale(),y*city.getScale(),nodes.size());
				nodes.add(node);
				matrix[(x-1)/2][(y-1)/2] = node;
				
				city.getBlock(x, y).setHighwayNode(node);
				city.getBlock(x, y-1).setHighwayNode(node);
				city.getBlock(x-1, y).setHighwayNode(node);
				city.getBlock(x-1, y-1).setHighwayNode(node);
			}
		}
		
		city.setHighwayNodes(nodes);
		
		for (int x=0; x<(city.getWidth()/2); x++)
		{
			for (int y=0; y<(city.getHeight()/2); y++)
			{
				Node from = matrix[x][y];
				
				for (int n=0; n<neighbourXs.length; n++)
				{
					
					int nx=x+neighbourXs[n];
					int ny=y+neighbourYs[n];
					
					if (nx>=0 && nx<(city.getWidth()/2) && ny>=0 && ny<(city.getHeight()/2))
					{
						Node to = matrix[nx][ny];
						
						SingleEdge edge = new SingleEdge(from,to);
						SingleEdge reverse = new SingleEdge(to,from);
						edge.setReverse(reverse);
						reverse.setReverse(edge);

						SingleEdge [] edges = new SingleEdge[1];
						edges[0] = edge;
						
						Highway fromTo = new Highway(from,to,edges,1);
						Highway toFrom = new Highway(to,from,fromTo.computeReverse(),1);
													
						fromTo.setReverse(toFrom);
						toFrom.setReverse(fromTo);
						
						from.addEdge(fromTo);
						to.addEdge(toFrom);
					}
					
				}
			}
		}
		
		return null;
		
		
	}
	
}
