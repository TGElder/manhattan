package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.Tube;
import elder.manhattan.Tubeway;
import elder.network.Edge;

public class ComputeTubeways implements Routine
{

	@Override
	public String run(Simulation simulation)
	{
				
		Block [] blocks = simulation.getCity().getBlocks();
		int noBlocks = blocks.length;
		
		int [] open = new int[noBlocks];
		int [] closed = new int[noBlocks];
		
		final Tube [] directions = new Tube[noBlocks];
		final double [] distances = new double[noBlocks];
		
		PriorityQueue<Block> openList = new PriorityQueue<Block> (new Comparator<Block>() 
		{

			@Override
			public int compare(Block a, Block b)
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
		
		for (Station station : simulation.getCity().getStations())
		{
			List<Station> neighbours = new ArrayList<Station> ();
			
			Block block = station.getBlock();
			int b=block.getIndex();

			session++;
			
			
			init(distances,Double.POSITIVE_INFINITY);
			openList.clear();
			
			openList.add(blocks[b]);
			open[b] = session;

			distances[b] = 0;
			directions[b] = null;
			
			Block focus;
			
			while ((focus = openList.poll())!=null)
			{
				if (focus==block||!focus.hasStation())
				{
					for (Edge edge : focus.getEdges())
					{
						Tube tube = (Tube)edge;
						Block neighbour  = tube.getTo();
						
						double focusDistance = distances[focus.getIndex()] + (tube.length/tube.getSpeed());
						
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
								
								directions[neighbour.getIndex()] = tube.getReverse();
								distances[neighbour.getIndex()] = focusDistance;
								
								openList.add(neighbour);
								
							}
						}
					}
				}
				else
				{
					neighbours.add(focus.getStation());
				}
				
				closed[focus.getIndex()] = session;
			}
			
			for (Station neighbour : neighbours)
			{
				
				Tubeway tubeway = getPath(neighbour,station,directions,distances);

				neighbour.addEdge(tubeway);
				
			

			}
			
		}
		
		return null;

	}
	
	private Tubeway getPath(Station from, Station to, Tube[] directions, double[] distances)
	{
		Block fromBlock = from.getBlock();
		Block toBlock = to.getBlock();
		
		List<Tube> tubes = new ArrayList<Tube> ();
		
		Block focus = fromBlock;

		while (focus!=toBlock)
		{
			Tube tube = directions[focus.getIndex()];
			focus = tube.getTo();
			
			tubes.add(tube);
		}
		
		Tube [] array = new Tube [tubes.size()];
		Tube [] reverseArray = new Tube [tubes.size()];

		
		for (int t=0; t<tubes.size(); t++)
		{
			array[t] = tubes.get(t);
			reverseArray[t] = tubes.get(tubes.size()-(t+1)).getReverse();
		}
		
		Tubeway out = new Tubeway(from,to,10,array);
		out.length = distances[fromBlock.getIndex()];
		Tubeway reverse = new Tubeway(to,from,10,reverseArray);
		reverse.length = distances[fromBlock.getIndex()];
		
		out.setReverse(reverse);
		reverse.setReverse(out);
		
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
