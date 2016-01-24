package elder.manhattan;

import java.util.ArrayList;
import java.util.List;

public class Wallet
{
	private int money;
	private List<WalletListener> listeners = new ArrayList<WalletListener> ();
	
	public Wallet(int money)
	{
		setMoney(money);
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public void setMoney(int money)
	{
		this.money = money;
		for (WalletListener listener : listeners)
		{
			listener.onChange();
		}
	}
	
	public void addMoney(int money)
	{
		this.money += money;
		for (WalletListener listener : listeners)
		{
			listener.onChange();
		}
	}
	
	public void addListener(WalletListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(WalletListener listener)
	{
		listeners.remove(listener);
	}
	
	public interface WalletListener
	{
		public void onChange();
	}

}
