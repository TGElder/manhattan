package elder.manhattan;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Station extends IndexNode
{

	private final Block block;
	private final Map<Service,Platform> platforms = new HashMap<Service,Platform> ();
	private String name;
	
	public Station(Block block, String name)
	{
		super(block.getTrackNode().x, block.getTrackNode().y, null);
		this.block = block;
		this.name = name;
	
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
	
	public Platform removePlatform(Service service) throws Exception
	{
		Platform platform = platforms.get(service);
		assert(platform!=null);
		platforms.remove(service);
		platform.remove();
		return platform;
	}

	public Collection<Platform> getPlatforms()
	{
		return platforms.values();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}


}
