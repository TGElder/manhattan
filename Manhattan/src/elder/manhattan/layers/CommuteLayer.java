package elder.manhattan.layers;

import java.util.HashSet;
import java.util.List;

import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.Commute;
import elder.manhattan.MultiEdge;
import elder.manhattan.SelectionListener;
import elder.manhattan.SingleEdge;
import elder.manhattan.Station;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.routines.Dijkstra;


public class CommuteLayer extends CityDrawerLayer implements SelectionListener<Block>
{

	private Block selectedBlock;
	
	private final City city;
	private final Dijkstra roadDijkstra;
	private final Dijkstra railDijkstra;

	
	public CommuteLayer(City city, Dijkstra roadDijkstra, Dijkstra railDijkstra)
	{
		super("Commute Layer");
		this.city = city;
		this.roadDijkstra = roadDijkstra;
		this.railDijkstra = railDijkstra;
	}

	@Override
	public void onSelect(Block selection)
	{
		selectedBlock = selection;
		refresh();
	}
	

	@Override
	public void draw()
	{
		
		if (selectedBlock!=null)
		{
			
			if (!selectedBlock.getResidents().isEmpty())
			{
				
				
				int [] commuters = new int[city.getBlocks().length]; 
				
				HashSet<Integer> commuted = new HashSet<Integer> ();
				
				for (Commute commute : selectedBlock.getResidents())
				{
					if (commute.getOffice()!=null)
					{
					
						int index = commute.getOffice().getTrackNode().getIndex();
						
						if (index!=selectedBlock.getTrackNode().getIndex())
						{	
							commuters[index] ++;
							commuted.add(index);
						}
					}
				}
				
				
				for (Integer b : commuted)
				{
					
					Block focus = city.getBlocks()[b];
										
					double distance = roadDijkstra.getDistances()[selectedBlock.getHighwayNode().getIndex()][focus.getHighwayNode().getIndex()];
					//double distance = Double.POSITIVE_INFINITY;
					
					Station from=null;
					Station to=null;
					
					for (Station s : selectedBlock.getStations())
					{
						for (Station s2: focus.getStations())
						{
							double focusDistance = railDijkstra.getDistances()[s.getIndex()][s2.getIndex()];
															
							if (focusDistance<distance)
							{
								from = s;
								to = s2;
								distance = focusDistance;
							}
							
						}
					}
					
					List<MultiEdge> path;
					
					if (from!=null)
					{
						path = railDijkstra.getPath(from, to);
												
					}
					else
					{
						path = roadDijkstra.getPath(selectedBlock.getHighwayNode(), focus.getHighwayNode());
					}
					
					
					if (path!=null)
					{
						
						for (MultiEdge multiEdge : path)
						{
							for (SingleEdge singleEdge : multiEdge.getEdges())
							{
								drawLine(singleEdge,1f,0f,0f,2f,false);
								drawLine(singleEdge.getReverse(),1f,0f,0f,2f,false);

							}
							
						}
					}
				}
			
			}
		}
		
	}


}