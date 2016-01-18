package megamu.mesh;


public class MPolygon {

	double[][] coords;
	int count;
	
	public MPolygon(){
		this(0);
	}

	public MPolygon(int points){
		coords = new double[points][2];
		count = 0;
	}

	public void add(double x, double y){
		coords[count][0] = x;
		coords[count++][1] = y;
	}



	public int count(){
		return count;
	}

	public double[][] getCoords(){
		return coords;
	}

}