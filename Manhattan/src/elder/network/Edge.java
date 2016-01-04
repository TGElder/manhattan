package elder.network;

import java.util.HashSet;

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
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return this==obj;
	}
	
	
	
}
