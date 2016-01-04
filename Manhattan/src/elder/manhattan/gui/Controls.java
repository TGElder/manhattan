
package elder.manhattan.gui;

import java.awt.Checkbox;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import elder.manhattan.City;
import elder.manhattan.Simulation;
import elder.manhattan.Tube;
import elder.manhattan.graphics.CityDrawer;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.layers.BlockLayer;
import elder.manhattan.layers.KatherineLayer;
import elder.manhattan.layers.SelectedBlockLayer;
import elder.manhattan.layers.TubeLayer;
import elder.manhattan.routines.AddChildren;
import elder.manhattan.routines.AddImmigrants;
import elder.manhattan.routines.Allocate;
import elder.manhattan.routines.Dijkstra;
import elder.manhattan.routines.OpenFields;
import elder.manhattan.routines.OpenRandomFields;
import elder.manhattan.routines.Populate;



public class Controls extends JFrame
{
	
	
	Controls(Simulation simulation, CityDrawer cityDrawer)
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		
		add(new SimulationControlPanel(simulation));
		
		JPanel layerPanel = new JPanel();
		layerPanel.setName("Layers");
		layerPanel.setBorder(BorderFactory.createTitledBorder(layerPanel.getName()));
		layerPanel.setLayout(new BoxLayout(layerPanel,BoxLayout.Y_AXIS));
		
		for (final CityDrawerLayer layer : cityDrawer.getLayers())
		{
			Checkbox checkbox = new Checkbox(layer.toString(),layer.enabled());
			
			checkbox.addItemListener(new ItemListener() 
			{

				public void itemStateChanged(ItemEvent e)
				{
					if (e.getStateChange()==1)
					{
						layer.enable();
					}
					else
					{
						layer.disable();
					}
				}
		         
			});
			layerPanel.add(checkbox);
		}
		
		add(layerPanel);
				
		pack();
		setVisible(true);
	}
	

	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		
		Simulation sim = new Simulation(new City(160,160,1),1986,1000);

		sim.run(new OpenRandomFields(0.005),false);
		sim.run(new Populate(1000),false);
		sim.addRoutine(new OpenFields(2.0/52.0));
		sim.addRoutine(new Allocate());
		sim.addRoutine(new AddImmigrants(50));
		sim.addRoutine(new AddChildren(50));
		
				
		CityDrawer cityDrawer = new CityDrawer(sim,1024,1024);
		
		Navigator navigator = new Navigator(cityDrawer);
		
		HoverSelector selector = new HoverSelector(sim);
		cityDrawer.addMouseListener(selector);
		

		
		BlockLayer blockLayer = new BlockLayer();
		cityDrawer.addLayer(blockLayer);
		
		TubeLayer tubeLayer = new TubeLayer();
		cityDrawer.addLayer(tubeLayer);

		
		KatherineLayer katherineLayer = new KatherineLayer();
		cityDrawer.addLayer(katherineLayer);
		katherineLayer.disable();
		
		SelectedBlockLayer selectedBlock = new SelectedBlockLayer();
		selector.getSelectedBlock().addListener(selectedBlock);
		cityDrawer.addLayer(selectedBlock);
		
		TubeBuilder tubeBuilder = new TubeBuilder();
		selector.getSelectedBlock().addListener(tubeBuilder);
		cityDrawer.addMouseListener(tubeBuilder);
		sim.addRoutine(tubeBuilder);
		sim.addRoutine(new Dijkstra());

		cityDrawer.addLayer(tubeBuilder);
		
		
		sim.addRoutine(blockLayer);
		sim.addRoutine(tubeLayer);
		sim.addRoutine(katherineLayer);
		

    	Controls controls = new Controls(sim,cityDrawer);

    	cityDrawer.run();


	}

}