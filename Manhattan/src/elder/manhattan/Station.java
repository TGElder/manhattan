package elder.manhattan;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Station extends RailwayNode
{

	private final Block block;
	private final Map<Service,Platform> platforms = new HashMap<Service,Platform> ();
	
	public Station(Block block)
	{
		super(block.x, block.y);
		this.block = block;
	
	}

	public Block getBlock()
	{
		return block;
	}
	
	public Platform getPlatform(Service service)
	{
		return platforms.get(service);
	}
	
	public Platform addPlatform(Service service)
	{
		Platform platform = new Platform(this,service);
		assert(!platforms.containsKey(service));
		platforms.put(service, platform);
		return platform;
	}
	
	public void removePlatform(Service service) throws Exception
	{
		Platform platform = platforms.get(service);
		assert(platforms.containsKey(service));
		platforms.remove(service);
		platform.remove();
	}

	public Collection<Platform> getPlatforms()
	{
		return platforms.values();
	}


}
