package elder.manhattan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Simulation implements Runnable
{
	public final double BLOCK_POPULATION_LIMIT;

	private final City city;

	private boolean halt;
	private boolean verbose=true;
	private int iterations=0;
	private int steps;
	
	
	private final Map<Routine,Long> runTime = new HashMap<Routine,Long> ();
	private final Map<Routine,Integer> runCount = new HashMap<Routine,Integer> ();

	private final List<Routine> routines = new ArrayList<Routine> ();
	
	private final Random random;
	
	public Simulation(City city, long seed, int BLOCK_POPULATION_LIMIT)
	{
		this.city = city;
		random = new Random(seed);
		this.BLOCK_POPULATION_LIMIT = BLOCK_POPULATION_LIMIT;
	}
	
	public void step()
	{
		halt = false;

		for (int s=0; s<steps; s++)
		{
			if (halt)
			{
				System.out.println("Simulation halted");
				return;
			}
			iterations ++;
			System.out.println(iterations+" ("+city.getPopulation()+")");
        	
        	for (Routine routine : routines)
        	{
        		run(routine,true);
        	}
        	        	
		}
	}
	
	private void print(String string)
	{
		if (verbose)
		{
			System.out.print(string);
		}
	}
	
	private void println(String string)
	{
		if (verbose)
		{
			System.out.println(string);
		}
	}

	public void run(Routine routine, boolean keepStats)
	{
		
		print("Running "+routine);
		
		long start = System.currentTimeMillis();
		String message = routine.run(this);
		long time = System.currentTimeMillis()-start;
				
		if (message!=null)
		{
			print(" ("+message+")");
		}
		
		print(" "+time+"ms");
		
		if (keepStats)
		{
			if (!runTime.containsKey(routine))
			{
				runTime.put(routine, 0L);
				runCount.put(routine,0);
			}
			
		
			runTime.put(routine,runTime.get(routine)+time);
			runCount.put(routine,runCount.get(routine)+1);

			print(" average "+runTime.get(routine)/runCount.get(routine) + "ms");
		}
		
		println("");

	}
	
	public void halt()
	{
		halt = true;
	}

	public City getCity()
	{
		return city;
	}

	@Override
	public void run()
	{
		step();
	}

	public void setSteps(int steps)
	{
		this.steps = steps;
	}
	
	public void update()
	{
    	for (Routine routine : routines )
		{
			run(routine,true);
		}
	}

	public void addRoutine(Routine routine)
	{
		routines.add(routine);
	}

	public Random getRandom()
	{
		return random;
	}


	
	
}
