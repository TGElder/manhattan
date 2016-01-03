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
		assert(home==null);
		this.home = home;
		home.getResidents().add(this);
	}
	
	public Block getOffice()
	{
		return office;
	}
	
	public void setOffice(Block office)
	{
		assert(office==null);
		this.office = office;
		office.getWorkers().add(this);
	}
	
	@Override
	public String toString()
	{
		return home+" to "+office;
	}
	
	
}
