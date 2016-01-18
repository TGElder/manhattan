package elder.geometry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import megamu.mesh.MPolygon;
import megamu.mesh.Delaunay;

public class DelaunayDiagrammer
{
	private static double SCALE=1000;
	
	public static List<Collection<Point>> draw(List<Point> points)
	{
		double[][] delaunayPoints = new double[points.size()][2];

		Double min = null;
		Double max = null;

		for (Point point : points)
		{
			if (min == null)
			{
				min = Math.min(point.x,point.y);
				max = Math.max(point.x,point.y);
			}
			else
			{
				min = Math.min(Math.min(min, point.x),point.y);
				max = Math.max(Math.max(max, point.x),point.y);
			
			}
		}
		
		for (int p = 0; p < points.size(); p++)
		{
			delaunayPoints[p][0] = ((points.get(p).x - min)/(max - min))*SCALE;
			delaunayPoints[p][1] = ((points.get(p).y - min)/(max - min))*SCALE;
		}
		
		Delaunay delaunay = new Delaunay(delaunayPoints);
		
		List<Collection<Point>> delaunayDiagram = new ArrayList<Collection<Point>> ();
		
		for (Point point : points)
		{
			delaunayDiagram.add(new ArrayList<Point> ());
		}
		
		int[][] links = delaunay.getLinks();

		for(int i=0; i<links.length; i++)
		{
		  int a = links[i][0];
		  int b = links[i][1];
		  
		  if (a!=b)
		  {
			  delaunayDiagram.get(a).add(points.get(b));
			  delaunayDiagram.get(b).add(points.get(a));
		  }

		}
		
		return delaunayDiagram;
		
	}

}
