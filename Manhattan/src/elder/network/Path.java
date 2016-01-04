package elder.network;

import java.util.ArrayList;

public class Path extends ArrayList<Edge>
{

	private static final long serialVersionUID = -6341442887288561448L;
	private ArrayList<Node> nodes = new ArrayList<Node> ();
	
	public ArrayList<Node> getNodes()
	{
		return nodes;
	}


}
