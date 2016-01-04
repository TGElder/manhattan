package elder.network;

public class PathfindingJob
{
	
	private final Journey journey;
	private Path path;
	
	PathfindingJob(Journey journey)
	{
		this.journey = journey;
	}
	
	public Journey getJourney()
	{
		return journey;
	}
	
	public Path getPath()
	{
		return path;
	}
	
	public void setPath(Path path)
	{
		this.path = path;
	}

}
