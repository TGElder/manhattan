package elder.manhattan.routines;

import java.util.ArrayList;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.HighwayNode;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;

public class CreateHighwayNodesFromFields implements Routine
{

	@Override
	public String run(Simulation simulation)
	{		
		List<HighwayNode> nodes = new ArrayList<HighwayNode> ();
		
		for (Block block : simulation.getCity().getBlocks())
		{
			if (block.isBuilt())
			{
				HighwayNode node = new HighwayNode(block,nodes.size());
				nodes.add(node);
			}
		}
		
		simulation.getCity().setHighwayNodes(nodes);
		
		for (Block block : simulation.getCity().getBlocks())
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
