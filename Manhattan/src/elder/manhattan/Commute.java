package elder.manhattan;

public class Commute
{
	
	private Block home;
	private Block office;
	
	public Block getHome()
	{
		return home;
	}
	
	public void setHome(Block home)
	{
		assert(this.home==null);
		this.home = home;
		home.addResident(this);
	}
	
	public Block getOffice()
	{
		return office;
	}
	
	public void setOffice(Block office)
	{
		assert(this.office==null);
		this.office = office;
		office.addWorker(this);
	}
	
	@Override
	public String toString()
	{
		return home+" to "+office;
	}
	
	
}
