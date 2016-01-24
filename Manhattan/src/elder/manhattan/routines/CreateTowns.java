package elder.manhattan.routines;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import elder.manhattan.Block;
import elder.manhattan.Routine;
import elder.manhattan.Simulation;
import elder.manhattan.Town;

public class CreateTowns implements Routine
{
	private final String fileName;

	public CreateTowns(String fileName)
	{
		this.fileName = fileName;
	}
	
	
	@Override
	public String run(Simulation simulation)
	{
		List<String> names = load(fileName);
		
		for (Block block : simulation.getCity().getBlocks())
		{
			Town town = new Town(names.get(simulation.getRandom().nextInt(names.size())));
			block.setTown(town);
			town.getBlocks().add(block);
		}

		return null;
		
	}

	private String processLine(ArrayList<String> line)
	{

		return line.get(1);

	}
	
	private List<String> load(String fileName)
	{
				
		List<String> out = new ArrayList<String> ();
		
		BufferedReader bufferedReader;
		String line;

		try
		{
			bufferedReader = new BufferedReader(new FileReader(fileName));
		
			StringTokenizer stringTokenizer;
			ArrayList<String> fields;
			
			try
			{
				while((line = bufferedReader.readLine()) != null)
				{
					stringTokenizer = new StringTokenizer(line,",");
					fields = new ArrayList<String> ();
					
					while (stringTokenizer.hasMoreTokens())
					{
						fields.add(stringTokenizer.nextToken());
					}
					
					out.add(processLine(fields));
					
				}
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			
		}
		
		return out;
			
	}

}
