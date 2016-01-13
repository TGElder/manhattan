package elder.manhattan.layers;

import elder.manhattan.Line;
import elder.manhattan.Service;
import elder.manhattan.Simulation;
import elder.manhattan.Tube;
import elder.manhattan.Tubeway;
import elder.manhattan.routines.PlaceTraffic;


public class ServiceLayer extends SimulationLayer
{
	private final PlaceTraffic traffic;
	
	public ServiceLayer(PlaceTraffic traffic)
	{
		super("Services");
		this.traffic = traffic;
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
						float width=1;

						if (traffic.getMaxTraffic()>0)
						{
							width += (tube.getTraffic()*9f)/(traffic.getMaxTraffic()*1f);
						}
						
						drawLine(tube,R,G,B,width,false);
					}
				}
			}
		}

	}

}
