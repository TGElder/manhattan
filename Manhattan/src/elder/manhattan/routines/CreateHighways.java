package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import elder.manhattan.Block;
import elder.manhattan.City;
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


	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
				
		int max = city.getBlocks().length;
		
		int [] open = new int[max];
		int [] closed = new int[max];
		
		final SingleEdge [] directions = new SingleEdge[max];
		final double [] distances = new double[max];
		
		PriorityQueue<IndexNode> openList = new PriorityQueue<IndexNode> (new Comparator<IndexNode>() 
		{

			@Override
			public int compare(IndexNode a, IndexNode b)
			{
				if (distances[a.getIndex()]<distances[b.getIndex()])
				{
					return -1;
				}
				else if (distances[a.getIndex()]>distances[b.getIndex()])
				{
					return 1;
				}
				else
				{
					return 0;
				}
					
			}
		});
		
		int session=0;
		
		for (HighwayNode node : simulation.getCity().getHighwayNodes())
		{
			Collection<HighwayNode> neighbours = getNeighbours(simulation.getCity(),node);
			
			Collection<IndexNode> targets = new HashSet<IndexNode> ();
			
			for (HighwayNode neighbour : neighbours)
			{
				targets.add(neighbour.getCentre().getRoadNode());
			}

			Block block = node.getCentre();
			int b=block.getRoadNode().getIndex();

			session++;
			
			
			init(distances,Double.POSITIVE_INFINITY);
			openList.clear();
			
			openList.add(block.getRoadNode());
			open[b] = session;

			distances[b] = 0;
			directions[b] = null;
						
			while (!targets.isEmpty())
			{
				IndexNode focus = openList.poll();
				
				for (Edge edge : focus.getEdges())
				{
					SingleEdge singleEdge = (SingleEdge)edge;
					IndexNode neighbour  = (IndexNode)singleEdge.b;
					
					double focusDistance = distances[focus.getIndex()] + (singleEdge.length);
					
					if (closed[neighbour.getIndex()]!=session)
					{
						if (focusDistance < distances[neighbour.getIndex()])
						{
							if (open[neighbour.getIndex()]==session)
							{
								openList.remove(neighbour);
							}
							else
							{
								open[neighbour.getIndex()] = session;
							}
							
							directions[neighbour.getIndex()] = singleEdge.getReverse();
							distances[neighbour.getIndex()] = focusDistance;
							
							openList.add(neighbour);
							
						}
					}
				}

				closed[focus.getIndex()] = session;
				
				targets.remove(focus);
				
				
			}
			
			for (HighwayNode neighbour : neighbours)
			{
				
				if (!node.hasEdge(neighbour))
				{
					link(neighbour,node,directions,distances);
				}
	
			}
			
		}
		
		return null;

	}
	
	private void link(HighwayNode from, HighwayNode to, SingleEdge[] directions, double[] distances)
	{
		IndexNode fromNode = from.getCentre().getRoadNode();
		IndexNode toNode = to.getCentre().getRoadNode();
		
		List<SingleEdge> edges = new ArrayList<SingleEdge> ();
		
		IndexNode focus = fromNode;

		while (focus!=toNode)
		{
			SingleEdge edge = directions[focus.getIndex()];
			focus = (IndexNode)(edge.b);
			
			edges.add(edge);
		}
		
		MultiEdge out = new MultiEdge(from,to,edges);
		MultiEdge reverse = new MultiEdge(to,from,out.computeReverse());
	
		out.setReverse(reverse);
		reverse.setReverse(out);
		
		from.addEdge(out);
		to.addEdge(reverse);
		
	}
	
	private Collection<HighwayNode> getNeighbours(City city, HighwayNode node)
	{
		Collection<HighwayNode> out = new HashSet<HighwayNode> ();

		for (Block member : node.getMembers())
		{
			for (Edge edge : member.getRoadNode().getEdges())
			{
				IndexNode to = (IndexNode)(edge.b);
				Block neighbour = city.getBlocks()[to.getIndex()];
				
				if (neighbour.getHighwayNode()!=node)
				{
					out.add(neighbour.getHighwayNode());

				}
				
			}
		}
		
		return out;
		
	}
	
	
	
	private void init(double [] array, double value)
	{
		for (int d=0; d<array.length; d++)
		{
			array[d] = value;
		}
	}
	
}
