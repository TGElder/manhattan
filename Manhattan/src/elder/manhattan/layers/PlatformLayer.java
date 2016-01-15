package elder.manhattan.layers;


import java.awt.Color;

import elder.geometry.Point;
import elder.geometry.Polygon;
import elder.manhattan.Platform;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.network.Node;


public class PlatformLayer extends SimulationLayer
{
	
	public PlatformLayer()
	{
		super("Platforms");
	}

	@Override
	public void draw(Simulation simulation)
	{
				
		for (Node node : simulation.getCity().getRailwayNodes())
		{
			if (node instanceof Station)
			{
				Station station = (Station)node;
				int offset=0;
				
				int platforms = station.getPlatforms().size();
				
				if (platforms>0)
				{
					double x1 = station.getBlock().getPolygon().getMin().x;
					double x2 = station.getBlock().getPolygon().getMax().x;
					double yIncrement = (station.getBlock().getPolygon().getMax().y - station.getBlock().getPolygon().getMin().y)/platforms;
					
					int p=0;
					
					for (Platform platform : station.getPlatforms())
					{
						double y1 = station.getBlock().getPolygon().getMin().y + (yIncrement*p);
						double y2 = y1+yIncrement;
						
						Polygon box = new Polygon();
						box.add(new Point(x1,y1));
						box.add(new Point(x2,y1));
						box.add(new Point(x2,y2));
						box.add(new Point(x1,y2));
						
						Color color = platform.getService().getLine().getColor();
						float R = color.getRed()/255f;
						float G = color.getGreen()/255f;
						float B = color.getBlue()/255f;
						
						drawPolygon(box,R,G,B,1,true);
						
						p++;
					}
				}
			}
			
			
		}
		
	}

}
