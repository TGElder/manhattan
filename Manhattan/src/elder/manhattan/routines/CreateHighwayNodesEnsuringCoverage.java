package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.HighwayNode;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class CreateHighwayNodesEnsuringCoverage implements Routine
{
	
	private final int coverage;
	
	public CreateHighwayNodesEnsuringCoverage(int coverage)
	{
		this.coverage = coverage;
	}
	
	@Override
	public String run(Simulation simulation)
	{
	
		
		City city = simulation.getCity();
		
		List<HighwayNode> nodes = new ArrayList<HighwayNode> ();
		
		for (int x=0; x<city.getWidth(); x+=coverage)
		{
			for (int y=0; y<city.getHeight(); y+=coverage)
			{
				int x2 = simulation.getRandom().nextInt(coverage);
				int y2 = simulation.getRandom().nextInt(coverage);
				
				Block block = simulation.getCity().getBlock(x+x2, y+y2);
				HighwayNode node = new HighwayNode(block,nodes.size());
				nodes.add(node);
			}
		}
		
		city.setHighwayNodes(nodes);
		
		for (Block block : city.getBlocks())
		{
			HighwayNode closestNode=  null;
			double closestDistance = Double.POSITIVE_INFINITY;
			
			for (HighwayNode focusNode : nodes)
			{
				double focusDistance = focusNode.getSquareDistanceTo(block.getRoadNode());
				
				if (closestNode==null||focusDistance<closestDistance)
				{
					closestNode = focusNode;
					closestDistance = focusDistance;
				}
			}
			
			block.setHighwayNode(closestNode);
		}
	
		return null;
		
	}


}
