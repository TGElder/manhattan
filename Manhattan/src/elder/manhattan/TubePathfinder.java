package elder.manhattan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import elder.network.Edge;

public class TubePathfinder
{

	private final City city;
	
	public TubePathfinder(City city)
	{
		this.city = city;
	}
	
	public List<Tube> findPath (Block from, Block to)
	{
			
		if (from==to)
		{
			return null;
		}
		
		Block [] blocks = city.getBlocks();
		int noBlocks = blocks.length;
		
		boolean [] open = new boolean[noBlocks];
		boolean [] closed = new boolean[noBlocks];
		
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
		
			
		
		
		Block block = from;
		int b=block.getIndex();		
		
		init(distances,Double.POSITIVE_INFINITY);
		openList.clear();
		
		openList.add(blocks[b]);
		open[b] = true;

		distances[b] = 0;
		directions[b] = null;
		
		Block focus;
		
		while ((focus = openList.poll())!=null)
		{
			
			for (Edge edge : focus.getEdges())
			{
				Tube tube = (Tube)edge;
				Block neighbour  = tube.getTo();
				
				double focusDistance = distances[focus.getIndex()] + (tube.length/tube.getSpeed());
				
				if (!closed[neighbour.getIndex()])
				{
					if (focusDistance < distances[neighbour.getIndex()])
					{
						if (open[neighbour.getIndex()])
						{
							openList.remove(neighbour);
						}
						else
						{
							open[neighbour.getIndex()] = true;
						}
						
						directions[neighbour.getIndex()] = tube.getReverse();
						distances[neighbour.getIndex()] = focusDistance;
						
						openList.add(neighbour);
						
					}
				}
			}
			
		
			if (focus==to)
			{
				return getPath(from,to,directions,distances);
			}
			
			closed[focus.getIndex()] = true;
		}
		
		
		return null;

	}
	
	private List<Tube> getPath(Block from, Block to, Tube[] directions, double[] distances)
	{

		List<Tube> tubes = new ArrayList<Tube> ();
		
		Block focus = to;

		while (focus!=from)
		{
			Tube tube = directions[focus.getIndex()];
			focus = tube.getTo();
			
			tubes.add(tube);
		}
		
		return tubes;

	}
	
	
	
	private void init(double [] array, double value)
	{
		for (int d=0; d<array.length; d++)
		{
			array[d] = value;
		}
	}
	
}
