package elder.manhattan;

import java.util.List;

import elder.manhattan.Pathfinder.Journey.Leg;
import elder.manhattan.routines.Dijkstra;

public class Pathfinder
{
	
	private final Dijkstra road;
	private final Dijkstra rail;
	private final double walkThreshold;
	
	public Pathfinder(Dijkstra road, Dijkstra rail, double walkThreshold)
	{
		this.road = road;
		this.rail = rail;
		this.walkThreshold = walkThreshold;
	}
	
	public void computeDistance(Journey journey)
	{
		Block from = journey.getFrom();
		Block to = journey.getTo();
		
		double distance = road.getDistances()[from.getHighwayNode().getIndex()][to.getHighwayNode().getIndex()];
		
		double [] distances = null;
		Station fromStation = null;
		Station toStation = null;
		
		for (Station s : from.getStations())
		{
			for (Station s2: to.getStations())
			{
				double [] focusDistances = new double[3];
				
				focusDistances[0] = road.getDistances()[from.getHighwayNode().getIndex()][s.getBlock().getHighwayNode().getIndex()];
				focusDistances[1] = rail.getDistances()[s.getIndex()][s2.getIndex()];
				focusDistances[2] = road.getDistances()[s2.getBlock().getHighwayNode().getIndex()][to.getHighwayNode().getIndex()];
				
				double focusDistance = focusDistances[0]+focusDistances[1]+focusDistances[2];
												
				if (focusDistance<distance)
				{
					distances = focusDistances;
					fromStation = s;
					toStation = s2;
					distance = focusDistance;
				}
				
			}
		}
		
		journey.setDistance(distance);
		
		Leg [] legs;
		
		if (fromStation!=null)
		{
			int mode;
			
			legs = new Leg[3];
			
			if (distances[0]<=walkThreshold)
			{
				mode = SingleEdge.FOOT;
			}
			else
			{
				mode = SingleEdge.BUS;
			}
			
			legs[0] = journey.new RoadLeg(from.getHighwayNode(),fromStation.getBlock().getHighwayNode(),mode,distances[0]);
			legs[1] = journey.new RailLeg(fromStation,toStation,distances[1]);
			
			if (distances[2]<=walkThreshold)
			{
				mode = SingleEdge.FOOT;
			}
			else
			{
				mode = SingleEdge.BUS;
			}
			
			legs[2] = journey.new RoadLeg(toStation.getBlock().getHighwayNode(),to.getHighwayNode(),mode,distances[2]);
	
		}
		else
		{
			legs = new Leg[1];
			legs[0] = journey.new RoadLeg(from.getHighwayNode(),to.getHighwayNode(),SingleEdge.CAR,distance);
		}
		
		journey.setLegs(legs);

	}
	
	public void computePaths(Journey journey)
	{
		for (Leg leg : journey.getLegs())
		{
			leg.setPath(leg.getDijkstra().getPath(leg.getFrom(), leg.getTo()));
		}
	}
	
	public class Journey
	{
		
		private final Block from;
		private final Block to;
		private double distance;
		private Leg [] legs;
		
		public Journey(Block from, Block to)
		{
			this.from = from;
			this.to = to;
		}
		
		public Block getFrom()
		{
			return from;
		}
		
		public Block getTo()
		{
			return to;
		}

		public double getDistance()
		{
			return distance;
		}

		private void setDistance(double distance)
		{
			this.distance = distance;
		}
		
		public Leg [] getLegs()
		{
			return legs;
		}

		private void setLegs(Leg [] legs)
		{
			this.legs = legs;
		}

		public abstract class Leg
		{
			private final IndexNode from;
			private final IndexNode to;
			private final int mode;
			private List<MultiEdge> path;
			private final double distance;
			
			public Leg(IndexNode from, IndexNode to, int mode, double distance)
			{
				this.from = from;
				this.to = to;
				this.mode = mode;
				this.distance = distance;
			}
			
			public IndexNode getFrom()
			{
				return from;
			}
			
			public IndexNode getTo()
			{
				return to;
			}
			
			public int getMode()
			{
				return mode;
			}
			
			public double getDistance()
			{
				return distance;
			}
			
			public abstract Dijkstra getDijkstra();

			public List<MultiEdge> getPath()
			{
				return path;
			}

			private void setPath(List<MultiEdge> path)
			{
				this.path = path;
			}
			
		}
		
		public class RoadLeg extends Leg
		{

			public RoadLeg(IndexNode from, IndexNode to, int mode, double distance)
			{
				super(from, to, mode, distance);
			}

			@Override
			public Dijkstra getDijkstra()
			{
				return road;
			}
			
		}
		
		public class RailLeg extends Leg
		{

			public RailLeg(IndexNode from, IndexNode to, double distance)
			{
				super(from, to, SingleEdge.RAIL, distance);
			}

			@Override
			public Dijkstra getDijkstra()
			{
				return rail;
			}
			
		}
		
		
	}
	

}
