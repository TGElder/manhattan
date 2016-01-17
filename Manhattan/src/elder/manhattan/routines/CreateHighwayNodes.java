package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.HighwayNode;
import elder.manhattan.IndexNode;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class CreateHighwayNodes implements Routine
{

	private static int [] catchmentXs = {-1, 0, 1,-1, 0, 1,-1, 0, 1};
	private static int [] catchmentYs = {-1,-1,-1, 0, 0, 0, 1, 1, 1};
	
	@Override
	public String run(Simulation simulation)
	{
		City city = simulation.getCity();
		
		List<HighwayNode> nodes = new ArrayList<HighwayNode> ();
		
		for(int x=1; x<city.getWidth(); x+=3)
		{
			for (int y=1; y<city.getHeight(); y+=3)
			{
				HighwayNode node = new HighwayNode(city.getBlock(x, y).getRoadNode().x,city.getBlock(x, y).getRoadNode().y,nodes.size(),city.getBlock(x, y));
				nodes.add(node);
				
				List<Block> members = new ArrayList<Block> ();
				
				for (int c=0; c<catchmentXs.length; c++)
				{
					int cx = x+catchmentXs[c];
					int cy = y+catchmentYs[c];
					
					if (cx>=0 && cx<city.getWidth() && cy>=0 && cy<city.getHeight())
					{
						city.getBlock(cx,cy).setHighwayNode(node);
						members.add(city.getBlock(cx, cy));
					}
				}
				
				node.setMembers(members.toArray(new Block[members.size()]));
			}
		}
		
		city.setHighwayNodes(nodes);
		
		return null;
	}

}
