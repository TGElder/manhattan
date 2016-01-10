package elder.manhattan.layers;

import elder.manhattan.Line;
import elder.manhattan.Service;
import elder.manhattan.Simulation;
import elder.manhattan.Tube;
import elder.manhattan.Tubeway;


public class ServiceLayer extends SimulationLayer
{
	
	public ServiceLayer()
	{
		super("Services");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		for (Line line : simulation.getCity().getLines())
		{
			float R=line.getColor().getRed()/255f;
			float G=line.getColor().getGreen()/255f;
			float B=line.getColor().getBlue()/255f;
			
			for (Service service : line.getServices())
			{
				for (Tubeway section : service.getSections())
				{
					for (Tube tube : section.getTubes())
					{
						drawLine(tube,R,G,B,4f,false);
					}
				}
			}
		}

	}

}
