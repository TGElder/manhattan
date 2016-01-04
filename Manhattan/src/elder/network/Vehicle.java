package elder.network;

import elder.network.AStarPathfinder.AStarThread;

public interface Vehicle
{
		
	public double getSpeed(Edge edge);
	
	public Path onFind(AStarThread thread, Path path);
	
}
