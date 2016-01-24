package elder.manhattan.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import elder.geometry.Point;
import elder.manhattan.Block;
import elder.manhattan.City;
import elder.manhattan.IndexNode;
import elder.manhattan.Line;
import elder.manhattan.Routine;
import elder.manhattan.SelectionListener;
import elder.manhattan.Service;
import elder.manhattan.Simulation;
import elder.manhattan.Station;
import elder.manhattan.Town;
import elder.manhattan.routines.Dijkstra;


public class StationBuilder extends Mode implements SelectionListener<Block>,Routine
{
	
	private final List<Block> toBuild = new ArrayList<Block> ();
	
	private Block to=null;

	private final City city;
	private final Dijkstra roadDijkstra;
	private final double threshold1;	
	private final double threshold2;

	public StationBuilder(City city, Dijkstra roadDijkstra, double threshold1, double threshold2)
	{
		super("Station Builder");
		this.city = city;
		this.roadDijkstra = roadDijkstra;
		this.threshold1 = threshold1;
		this.threshold2 = threshold2;
	}
	
	@Override
	public void onSelect(Block selection)
	{
		to = selection;
		
		refresh();
	}

	@Override
	public void onMove(Point cityPoint)
	{
		
	}

	@Override
	public void onLeftClick(Point cityPoint)
	{
		toBuild.add(to);
		
		refresh();

	}

	@Override
	public void onMiddleClick(Point cityPoint)
	{
	}

	@Override
	public void onRightClick(Point cityPoint)
	{
		if (to.hasStation())
		{
			new StationEditor(to.getStation());
		}
	}

	@Override
	public void onLeftDrag(Point screenOffset)
	{
		
	}

	@Override
	public void onMiddleDrag(Point screenOffset)
	{
		
	}

	@Override
	public void onRightDrag(Point screenOffset)
	{
		
	}

	@Override
	public void onWheelUp()
	{
		
	}

	@Override
	public void onWheelDown()
	{
		
	}

	@Override
	public String run(Simulation simulation)
	{
		for (Block block : toBuild )
		{
			
			try
			{
				if (!block.hasStation())
				{
					simulation.getCity().createStation(block,recommendStationName(simulation.getCity().getRailwayNodes(),block));
				}
				else
				{
					simulation.getCity().removeStation(block);
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		
		}
		
		toBuild.clear();

		
		return null;
	}

	public String recommendStationName(List<IndexNode> nodes, Block block)
	{
		List<Town> towns = new ArrayList<Town> ();
		
		Town focus = block.getTown();
		
		while (focus!=null)
		{
			towns.add(0,focus);
			focus = focus.getParent();
		}
		
		for (Town town : towns)
		{
			if (!stationExistsWithName(nodes,town.getName()))
			{
				return new String(town.getName());
			}
		}
		
		return "New Station";
		
		
	}
	
	private boolean stationExistsWithName(List<IndexNode> nodes, String string)
	{
		for (IndexNode node : nodes)
		{
			if (node instanceof Station)
			{
				Station station = (Station)node;
				
				if (station.getName().equals(string))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void draw()
	{
		
		if (to!=null)
		{
			
			
			
			
			drawPolygon(to.getPolygon(),1f,1f,0,0.5f,true);
			
			for (Block block : city.getBlocks())
			{
				double distance = roadDijkstra.getDistances()[to.getHighwayNode().getIndex()][block.getHighwayNode().getIndex()];
				
				if (distance<=threshold1)
				{
					drawPolygon(block.getPolygon(),1f,1f,0,0.5f,true);
				}
				else if (distance<=threshold2)
				{
					drawPolygon(block.getPolygon(),1f,1f,0,0.25f,true);
				}
				
				
			}
		}
		
		
	}
	
	@Override
	public void reset()
	{

	}

	private class StationEditor extends JFrame
	{
		
		public StationEditor(Station station)
		{	
			setTitle("Edit Station");
			
			setLayout(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth=1;
			constraints.gridheight=1;
			constraints.weightx = 1;
			constraints.weighty = 1;
			
			JTextField textField = new JTextField();
			textField.setText(station.getName());
			textField.selectAll();
			constraints.gridy = 0;
			constraints.gridwidth=2;
			constraints.weighty = 0;
			add(textField,constraints);
						
			JButton okay = new JButton("OK");
			okay.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e)
				{
					station.setName(textField.getText());
					dispose();
				}});
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.gridwidth=1;
			constraints.weighty = 0;
			add(okay,constraints);
			
			JButton cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}});
			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.gridwidth=1;
			constraints.weighty = 0;
			add(cancel,constraints);
			
			pack();
			setVisible(true);

		}
		

	}

}
