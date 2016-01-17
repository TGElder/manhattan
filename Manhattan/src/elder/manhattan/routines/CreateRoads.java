package elder.manhattan.routines;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.IndexNode;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;

public class CreateRoads implements Routine
{

	private static int [] catchmentXs = {-1, 0, 1,-1, 1,-1, 0, 1};
	private static int [] catchmentYs = {-1,-1,-1, 0, 0, 1, 1, 1};
	
	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
				
		for(int x=0; x<city.getWidth(); x++)
		{
			for (int y=0; y<city.getHeight(); y++)
			{
				Block block = city.getBlock(x, y);
				IndexNode roadNode = block.getRoadNode();
				int index = (y*city.getWidth())+x;
				
				for (int c=0; c<catchmentXs.length; c++)
				{
					int cx = x+catchmentXs[c];
					int cy = y+catchmentYs[c];
					
					if (cx>=0 && cx<city.getWidth() && cy>=0 && cy<city.getHeight())
					{
						Block other = city.getBlock(cx, cy);
						IndexNode otherRoadNode = other.getRoadNode();
						int otherIndex = (cy*city.getWidth())+cx;
						
						if (otherIndex>index)
						{
							SingleEdge road = new SingleEdge(roadNode,otherRoadNode);
							SingleEdge reverse = new SingleEdge(otherRoadNode,roadNode);
							
							road.setReverse(reverse);
							reverse.setReverse(road);
							
							roadNode.addEdge(road);
							otherRoadNode.addEdge(reverse);
						}
					}
				}
			}
		}
		
		return null;
	}

}
