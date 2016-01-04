package elder.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AStarPathfinder implements Runnable
{

	final Map<Node,Map<Node,Path>> preComputedPaths = new HashMap<Node,Map<Node,Path>> ();
	final PriorityQueue<AStarNode> openList = new PriorityQueue<AStarNode> ();
	
	private final LinkedBlockingQueue<PathfindingJob> in = new LinkedBlockingQueue<PathfindingJob> ();
	private final LinkedBlockingQueue<PathfindingJob> out = new LinkedBlockingQueue<PathfindingJob> ();
 
	private final List<AStarThread> sessions = new ArrayList<AStarThread> ();
	private final Map<Node, AStarNode[]> node2node = new HashMap<Node,AStarNode[]> ();
	
	public AStarPathfinder()
	{
		int cores = Runtime.getRuntime().availableProcessors();
		
		for (int c=0; c<cores-1; c++)
		{
			AStarThread session = new AStarThread(node2node,c,in,out);
			sessions.add(session);
			new Thread(session).start();
		}
	}
	
	public LinkedBlockingQueue<PathfindingJob> getOut()
	{
		return out;
	}
	
	public PathfindingJob findPath(Journey journey)
	{
		PathfindingJob job = new PathfindingJob(journey);
		in.add(job);
		return job;
	}
	
	
	public void stop()
	{
		in.clear();
		preComputedPaths.clear();
		while(!done())
		{
			
		}
	}
	
	public boolean done()
	{
		for (AStarThread session : sessions)
		{
			if (!session.done())
			{
				return false;
			}
		}
		
		return true;
	}

	public class AStarThread implements Runnable
	{
		
		final PriorityQueue<AStarNode> openList = new PriorityQueue<AStarNode> ();
		
		final LinkedBlockingQueue<PathfindingJob> in;
		final LinkedBlockingQueue<PathfindingJob> out;
		
		private boolean done=true;
		
		private final Map<Node,AStarNode[]> node2node;
		final int thread;
		
		private AStarNode from;
		private AStarNode to;
		private int session=0;
		
		
		AStarThread(Map<Node,AStarNode[]> node2node, int thread, LinkedBlockingQueue<PathfindingJob> in, LinkedBlockingQueue<PathfindingJob> out)
		{
			this.node2node = node2node;
			this.thread = thread;
			this.in = in;
			this.out = out;
		}

		private final double heuristic(AStarNode node)
		{
			return (node.node.getDistanceTo(to.node)*1);
		}
		
		private final void close(AStarNode node)
		{
			node.closed = session;
			node.open = 0;
		}
		
		private final boolean isClosed(AStarNode node)
		{
			return node.closed==session;
		}
		
		private final void open(AStarNode node, AStarNode parent, int edge, double time)
		{
			node.open = session;
			node.H = heuristic(node);
			update(node, parent,edge,time);
		}
		
		private final boolean isOpen(AStarNode node)
		{
			return node.open==session;
		}
		
		private final void update(AStarNode node, AStarNode parent, int edge, double time)
		{
			node.parent = parent;
			node.edge = edge;
			node.G = parent.G + time;
			node.F = node.G + node.H;
		}
	
		public Path findPath(Journey journey)
		{

			session++;
			
			from = node2node.get(journey.from)[thread];
			to = node2node.get(journey.to)[thread];
			
			openList.clear();

			from.open = session;
			from.parent = null;
			from.G = 0;
			from.H = heuristic(from);
			from.F = from.H;
			
			openList.add(from);

			while (openList.size()>0)
			{
				AStarNode focus = openList.poll();
				
				
				for (int n=0; n<focus.neighbourCount; n++)
				{
					
					AStarNode neighbour = focus.neighbours[n];
					Edge edge = focus.edges[n];
					
					if (
							journey.vehicle.getSpeed(edge)>0
							&&
							(
									!edge.isTerminal()
									||
									edge.a==journey.from
									||
									edge.b==journey.to
							)
						)
					{
					
						if (!isClosed(neighbour)) //#TODO Check before getting the edge
						{
							
							double time = edge.length/journey.vehicle.getSpeed(edge);
							if (!isOpen(neighbour))
							{
								open(neighbour,focus,n,time);
								
								openList.add(neighbour);
								
							}
							else
							{
								
								if ((focus.G + time) < neighbour.G)
								{
									openList.remove(neighbour);
									update(neighbour,focus,n,time);
									openList.add(neighbour);
								}
							}
						}
						
					}
					
				}
				
				close(focus);
				
				if (focus==to)
				{
					return journey.vehicle.onFind(this,buildPath());
				}
				
			}

			return null;
		}
		
		private Path buildPath()
		{
			AStarNode focusTo = to;
			AStarNode focusFrom;
			
			Path path = new Path ();
			
			path.getNodes().add(focusTo.node);
		
			while (focusTo.parent!=null)
			{
				focusFrom = focusTo.parent;
				path.add(0,focusFrom.edges[focusTo.edge]);
				path.getNodes().add(0,focusFrom.node);
				
				focusTo = focusFrom;
			}
			
//			for (int e=0; e<path.size(); e++)
//			{
//				assert(path.get(e).a.equals(path.getNodes().get(e)));
//				assert(path.get(e).b.equals(path.getNodes().get(e+1)));
//			}
			
			return path;
			
		}


		@Override
		public void run()
		{
			while (true)
			{
				PathfindingJob job;
				try
				{
					job = in.take();
										
					done=false;
					job.setPath(findPath(job.getJourney()));
					out.add(job);
					done=true;
					
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		public boolean done()
		{
			return done;
		}
		
	}
	
	public class AStarNode implements Comparable<AStarNode>
	{
		final Node node;
		
		AStarNode parent;
		public int edge;
		
		final int neighbourCount;
		
		final AStarNode [] neighbours;
		final Edge [] edges;
		
		double F;
		double G;
		double H;
		
		int open=0;
		int closed=0;

		AStarNode(Node node)
		{
			
			this.node = node;
			
			this.neighbourCount = node.getEdges().size();
			
			neighbours = new AStarNode[neighbourCount];
			edges = new Edge[neighbourCount];
	
		}

		
		@Override
		public int compareTo(AStarNode other)
		{
			if (F>other.F)
			{
				return 1;
			}
			else if (other.F>F)
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
		
		@Override
		public String toString()
		{
			return "("+node+" p"+parent+" f"+F+" g"+G+" h"+H+")";
		}
	}



	@Override
	public void run()
	{
//		while(true)
//		{
//			
//			if (!forPostProcessing.isEmpty())
//			{
//				PathfindingJob job = forPostProcessing.poll();
//				//addToLibrary(job.getPath());
//				out.add(job);
//
//			}
//			else if (!in.isEmpty())
//			{
//				PathfindingJob job = in.poll();
//				//forPathfinding.add(job);
//				
//				//Path path = getFromLibrary(job.getJourney());
//				
////				if (path!=null)
////				{
////					job.setPath(path);
////					out.add(job);
////				}
////				else
////				{
//				if (job!=null)
//				{
//					forPathfinding.add(job);
//				}
////				}
//			}
//		}
		
	}

	public void init(Collection<Node> nodes)
	{
		
		node2node.clear();
		
		for (Node node : nodes)
		{
			AStarNode [] aStarNodes = new AStarNode[sessions.size()];
			
			for (int s=0; s<sessions.size(); s++)
			{
				aStarNodes[s] = new AStarNode(node);
			}
			
			node2node.put(node,aStarNodes);
		}
		
		for (Map.Entry<Node, AStarNode[]> node2nodeEntry : node2node.entrySet())
		{
			int n=0;
			for (Edge edge : node2nodeEntry.getKey().getEdges())
			{
				
				for (int s=0; s<sessions.size(); s++)
				{
					node2nodeEntry.getValue()[s].neighbours[n] = node2node.get(edge.b)[s];
					node2nodeEntry.getValue()[s].edges[n] = edge;

				}
				
				n++;

			}
		}

	}



}
