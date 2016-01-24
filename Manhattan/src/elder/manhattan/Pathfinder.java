package elder.manhattan;

import java.util.List;

import elder.manhattan.Pathfinder.Journey.Leg;
import elder.manhattan.routines.Dijkstra;

public class Pathfinder
{
	
	private final Dijkstra road;
	private final Dijkstra rail;
	
	public Pathfinder(Dijkstra road, Dijkstra rail)
	{
		this.road = road;
		this.rail = rail;
	}
	
	public void computeDistance(Journey journey)
	{
		Block from = journey.getFrom();
		Block to = journey.getTo();
		
		double distance = road.getDistances()[from.getHighwayNode().getIndex()][to.getHighwayNode().getIndex()];
		
		Station fromStation = null;
		Station toStation = null;
		
		for (Station s : from.getStations())
		{
			for (Station s2: to.getStations())
			{
				double to2station = road.getDistances()[from.getHighwayNode().getIndex()][s.getBlock().getHighwayNode().getIndex()];
				double station2station = rail.getDistances()[s.getIndex()][s2.getIndex()];
				double station2from = road.getDistances()[s2.getBlock().getHighwayNode().getIndex()][to.getHighwayNode().getIndex()];
				
				double focusDistance = to2station+station2station+station2from;
												
				if (focusDistance<distance)
				{
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
			
			double to2station = road.getDistances()[from.getHighwayNode().getIndex()][fromStation.getBlock().getHighwayNode().getIndex()];
			if (to2station<=2)
			{
				mode = SingleEdge.FOOT;
			}
			else
			{
				mode = SingleEdge.BUS;
			}
			
			legs[0] = journey.new RoadLeg(from.getHighwayNode(),fromStation.getBlock().getHighwayNode(),mode);
			legs[1] = journey.new RailLeg(fromStation,toStation);
			
			double station2from = road.getDistances()[toStation.getBlock().getHighwayNode().getIndex()][to.getHighwayNode().getIndex()];
			if (station2from<=2)
			{
				mode = SingleEdge.FOOT;
			}
			else
			{
				mode = SingleEdge.BUS;
			}
			
			legs[2] = journey.new RoadLeg(toStation.getBlock().getHighwayNode(),to.getHighwayNode(),SingleEdge.BUS);
	
		}
		else
		{
			legs = new Leg[1];
			legs[0] = journey.new RoadLeg(from.getHighwayNode(),to.getHighwayNode(),SingleEdge.CAR);
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
			
			public Leg(IndexNode from, IndexNode to, int mode)
			{
				this.from = from;
				this.to = to;
				this.mode = mode;
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

			public RoadLeg(IndexNode from, IndexNode to, int mode)
			{
				super(from, to, mode);
			}

			@Override
			public Dijkstra getDijkstra()
			{
				return road;
			}
			
		}
		
		public class RailLeg extends Leg
		{

			public RailLeg(IndexNode from, IndexNode to)
			{
				super(from, to, SingleEdge.RAIL);
			}

			@Override
			public Dijkstra getDijkstra()
			{
				return rail;
			}
			
		}
		
		
	}
	

}
