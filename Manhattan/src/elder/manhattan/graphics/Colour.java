package elder.manhattan.graphics;

import java.util.Random;



public class Colour
{

	static Random random = new Random();
	
	public float R;
	public float G;
	public float B;
	
	public Colour()
	{
		R = random.nextFloat();
		G = random.nextFloat();
		B = random.nextFloat();
	}
}