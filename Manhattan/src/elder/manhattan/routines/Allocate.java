package elder.manhattan.routines;

import java.util.Collection;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;

public class Allocate implements Routine
{

	private final Dijkstra railDijkstra;
	private final Dijkstra roadDijkstra;
	
	public Allocate(Dijkstra railDijkstra, Dijkstra roadDijkstra)
	{
		this.railDijkstra = railDijkstra;
		this.roadDijkstra = roadDijkstra;
	}
	
	@Override
	public String run(Simulation simulation)
	{
		allocate(simulation,simulation.getCity().getHomeless(),true);
		allocate(simulation,simulation.getCity().getUnemployed(),false);
		return null;
	}
	
	private void allocate(Simulation simulation, Collection<Commute> commutes, boolean homeless)
	{
		City city = simulation.getCity();
		
		double [][] gravity = new double[city.getWidth()][city.getHeight()];
		
		double max=0;
		
		for (int x=0; x<city.getWidth(); x++)
		{
			for (int y=0; y<city.getWidth(); y++)
			{
				
				Block block = city.getBlock(x, y);
				
				if (homeless)
				{
					gravity[x][y] = block.getResidents().size();
				}
				else
				{
					gravity[x][y] = block.getWorkers().size();
				}
				
				for (Block neighbour : block.getNeighbours())
				{
					if (homeless)
					{
						gravity[x][y] += neighbour.getResidents().size();
					}
					else
					{
						gravity[x][y] += neighbour.getWorkers().size();
					}
				}
				
				max = Math.max(max, gravity[x][y]);
			}
		}
		
		
		if (max>0)
		{
			for (int x=0; x<city.getWidth(); x++)
			{
				for (int y=0; y<city.getWidth(); y++)
				{
					gravity[x][y] /= max;
				}
			}
		}
		
		for (Commute commute : commutes)
		{
			Block block;
			if (homeless)
			{
				block = commute.getOffice();
			}
			else
			{
				block = commute.getHome();
			}
									
			
			double score[] = new double[city.getWidth()*city.getHeight()];
			double totalScore=0.0;
			
			double maxDistance = Math.max(block.getX(), city.getWidth()-block.getX()) + Math.max(block.getY(), city.getHeight()-block.getY());
			
			for (int x=0; x<city.getWidth(); x++)
			{
				for (int y=0; y<city.getWidth(); y++)
				{
					Block focus = city.getBlock(x, y);
										
					
					if (focus.isBuilt()&&focus.getPopulation()<simulation.BLOCK_POPULATION_LIMIT)
					{
						double distance = roadDijkstra.getDistances()[block.getHighwayNode().getIndex()][focus.getHighwayNode().getIndex()];
						
						for (Station s : block.getStations())
						{
							for (Station s2: focus.getStations())
							{
								double toStation = roadDijkstra.getDistances()[block.getHighwayNode().getIndex()][s.getBlock().getHighwayNode().getIndex()];
								double station2station = railDijkstra.getDistances()[s.getIndex()][s2.getIndex()];
								double fromStation = roadDijkstra.getDistances()[s2.getBlock().getHighwayNode().getIndex()][block.getHighwayNode().getIndex()];
								distance = Math.min(distance, toStation + station2station + fromStation);
							}
						}
						
						double distanceScore = distance/maxDistance;
						
						double blockScore = distanceScore*1.0 + gravity[x][y]*0.0;
						blockScore = 1/Math.pow(2,distanceScore*10);
						
						score[(y*city.getWidth())+x]=blockScore;
						totalScore += blockScore;
					}
				}
			}
			
			double wheel = simulation.getRandom().nextDouble()*totalScore;
			
			double cumulative=0.0;
			
			int index=0;
			
			while (cumulative<wheel)
			{
				cumulative += score[index];
				index++;
			}
			
			assert(score[index-1]!=0);
			
			
			if (homeless)
			{
				commute.setHome(city.getBlocks()[index-1]);
			}
			else
			{
				commute.setOffice(city.getBlocks()[index-1]);
			}

			//System.out.println(commute);

		}
		
		commutes.clear();
		
	}
	

}
