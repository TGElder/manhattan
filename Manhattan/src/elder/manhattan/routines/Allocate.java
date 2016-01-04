package elder.manhattan.routines;

import java.util.Collection;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class Allocate implements Routine
{

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
					if (city.getBlock(x, y).isBuilt()&&city.getBlock(x, y).getPopulation()<simulation.BLOCK_POPULATION_LIMIT)
					{
					
						double distanceScore = (Math.abs(block.getX() - x) + Math.abs(block.getY() - y));
						distanceScore /= maxDistance;
						distanceScore = 1 - distanceScore;
						distanceScore = Math.pow(distanceScore,2);
						
						double blockScore = distanceScore*0.5 + gravity[x][y]*0.5;
						
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
