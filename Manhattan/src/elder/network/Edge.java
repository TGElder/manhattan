package elder.network;

import elder.geometry.Line;

public class Edge extends Line
{
	
	public Edge(Node A, Node B)
	{
		super(A,B);
	}
	
	public boolean isTerminal()
	{
		return false;
	}
	

	
	
}
