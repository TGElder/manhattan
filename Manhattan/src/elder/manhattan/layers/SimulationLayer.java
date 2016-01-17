package elder.manhattan.layers;

import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.graphics.CityDrawerLayer;

public abstract class SimulationLayer extends CityDrawerLayer implements Routine
{
	private Simulation simulation;
	
	public SimulationLayer(String name)
	{
		super(name);
	}
	
	public void draw()
	{
		draw(simulation);
	}

	public abstract void draw(Simulation simulation);

	@Override
	public String run(Simulation simulation)
	{
		this.simulation = simulation;
		refresh();
		return null;
	}

	
}
