package elder.manhattan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation implements Runnable
{
	private final City city;

	private boolean halt;
	private boolean verbose;
	private int iterations=0;
	private int steps;
	
	private final List<Routine> routines = new ArrayList<Routine> ();
	
	private final Map<Routine,Long> runTime = new HashMap<Routine,Long> ();
	private final Map<Routine,Integer> runCount = new HashMap<Routine,Integer> ();
	
	public Simulation(City city)
	{
		this.city = city;
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
			System.out.println(iterations);
        	
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

	private void run(Routine routine, boolean keepStats)
	{
		
		print("Running "+routine);
		
		long start = System.currentTimeMillis();
		routine.run();
		long time = System.currentTimeMillis()-start;
		
		String message = routine.getMessage();
		
		if (message.length()>0)
		{
			print(" ("+routine.getMessage()+")");
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
	
}