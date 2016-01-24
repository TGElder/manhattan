package elder.manhattan.routines;

import java.util.Collection;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.Pathfinder;
import elder.manhattan.Pathfinder.Journey;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Station;

public class Allocate implements Routine
{

	private final Pathfinder pathfinder;
	
	public Allocate(Pathfinder pathfinder)
	{
		this.pathfinder = pathfinder;
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
						Journey journey = pathfinder.new Journey(block,focus);
						pathfinder.computeDistance(journey);
				
						double distanceScore = journey.getDistance()/maxDistance;
						
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
