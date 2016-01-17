
package elder.manhattan.gui;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import elder.manhattan.City;
import elder.manhattan.Line;
import elder.manhattan.Service;
import elder.manhattan.Simulation;
import elder.manhattan.graphics.CityDrawer;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.layers.BlockLayer;
import elder.manhattan.layers.DijkstraLayer;
import elder.manhattan.layers.KatherineLayer;
import elder.manhattan.layers.PathfindTestLayer;
import elder.manhattan.layers.PlatformLayer;
import elder.manhattan.layers.SelectedBlockLayer;
import elder.manhattan.layers.ServiceLayer;
import elder.manhattan.layers.StationCoverageLayer;
import elder.manhattan.layers.StationLayer;
import elder.manhattan.layers.TimeLayer;
import elder.manhattan.layers.TubeLayer;
import elder.manhattan.routines.AddChildren;
import elder.manhattan.routines.AddImmigrants;
import elder.manhattan.routines.Allocate;
import elder.manhattan.routines.Dijkstra;
import elder.manhattan.routines.OpenFields;
import elder.manhattan.routines.OpenRandomFields;
import elder.manhattan.routines.PlaceTraffic;
import elder.manhattan.routines.Populate;
import elder.manhattan.routines.UpdateStations;



public class Controls extends JFrame
{
	
	
	Controls(Simulation simulation, CityDrawer cityDrawer, List<Mode> modes, ModeManager manager, ServiceBuilder serviceBuilder)
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth=2;
		constraints.gridheight=1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		add(new SimulationControlPanel(simulation),constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		constraints.gridheight=1;

		add(new ModePanel(modes, manager),constraints);
		
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
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		
		add(layerPanel,constraints);
				
		

		LinePanel linePanel = new LinePanel(simulation.getCity());
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth=2;
		constraints.gridheight=1;
		add(linePanel,constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth=2;
		constraints.gridheight=1;
		
		add(new ServicePanel(simulation.getCity(),linePanel.getSelection(),serviceBuilder),constraints);
		
		pack();
		setVisible(true);
	}
	

	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		
		Simulation sim = new Simulation(new City(160,160,1),1986,500);

		CityDrawer cityDrawer = new CityDrawer(sim,1024,1024);
		
		Navigator navigator = new Navigator(cityDrawer);
		
		
		Dijkstra dijkstra = new Dijkstra();	

		
		HoverSelector selector = new HoverSelector(sim);
		cityDrawer.addMouseListener(selector);
		
		sim.run(new OpenRandomFields(0.005),false);
		sim.run(new Populate(1000),false);
		
		//sim.run(new CreateTestTubes(),false);
		
		TrackBuilder trackBuilder = new TrackBuilder();
		selector.getSelectedBlock().addListener(trackBuilder);
		
		ServiceBuilder serviceBuilder = new ServiceBuilder(sim.getCity());
		selector.getSelectedBlock().addListener(serviceBuilder);
		
		StationBuilder stationBuilder = new StationBuilder();
		selector.getSelectedBlock().addListener(stationBuilder);
		
		sim.addRoutine(new OpenFields(2.0/52.0));
		
		
		sim.addRoutine(new UpdateStations());
		
		//sim.addRoutine(new ComputeTubeways());

		sim.addRoutine(dijkstra);
		sim.addRoutine(new Allocate(dijkstra));
		PlaceTraffic placeTraffic = new PlaceTraffic(dijkstra);
		sim.addRoutine(placeTraffic);
		sim.addRoutine(new AddImmigrants(50));
		sim.addRoutine(new AddChildren(50));
		
				
		
		
		
		
		
		BlockLayer blockLayer = new BlockLayer();
		cityDrawer.addLayer(blockLayer);
		
		TubeLayer tubeLayer = new TubeLayer();
		StationLayer stationLayer = new StationLayer();
		PlatformLayer platformLayer = new PlatformLayer();
		StationCoverageLayer stationCoverageLayer = new StationCoverageLayer();
		
		KatherineLayer katherineLayer = new KatherineLayer();
		cityDrawer.addLayer(katherineLayer);
		katherineLayer.disable();
		
		SelectedBlockLayer selectedBlock = new SelectedBlockLayer();
		selector.getSelectedBlock().addListener(selectedBlock);
		
		

		
		
		DijkstraLayer dijkstraLayer = new DijkstraLayer(dijkstra);
		selector.getSelectedBlock().addListener(dijkstraLayer);
		cityDrawer.addLayer(dijkstraLayer);
		
		PathfindTestLayer pathfindTestLayer = new PathfindTestLayer(dijkstra);
		selector.getSelectedBlock().addListener(pathfindTestLayer);
		cityDrawer.addLayer(pathfindTestLayer);
		pathfindTestLayer.disable();
		
		
		
		TimeLayer timeLayer = new TimeLayer(sim.getCity(),dijkstra);
		selector.getSelectedBlock().addListener(timeLayer);
		cityDrawer.addLayer(timeLayer);
		timeLayer.disable();
		
		ServiceLayer serviceLayer = new ServiceLayer(placeTraffic);
		
		cityDrawer.addLayer(stationCoverageLayer);
		cityDrawer.addLayer(tubeLayer);
		cityDrawer.addLayer(serviceLayer);
		cityDrawer.addLayer(stationLayer);
		cityDrawer.addLayer(platformLayer);
		platformLayer.disable();

		
	
		
		cityDrawer.addLayer(trackBuilder);
		cityDrawer.addLayer(stationBuilder);
		cityDrawer.addLayer(serviceBuilder);


		sim.addRoutine(trackBuilder);
		sim.addRoutine(stationBuilder);
		sim.addRoutine(serviceBuilder);


		sim.addRoutine(blockLayer);
		sim.addRoutine(stationCoverageLayer);
		sim.addRoutine(tubeLayer);
		sim.addRoutine(serviceLayer);
		sim.addRoutine(stationLayer);
		sim.addRoutine(platformLayer);

		
		sim.addRoutine(katherineLayer);
		

		List<Mode> modes = new ArrayList<Mode> ();
		modes.add(trackBuilder);
		modes.add(stationBuilder);
		modes.add(serviceBuilder);
		
		
		sim.addPauseRoutine(trackBuilder);
		sim.addPauseRoutine(stationBuilder);
		sim.addPauseRoutine(serviceBuilder);
		
		sim.addPauseRoutine(blockLayer);
		sim.addPauseRoutine(stationCoverageLayer);
		sim.addPauseRoutine(tubeLayer);
		sim.addPauseRoutine(serviceLayer);
		sim.addPauseRoutine(stationLayer);
		sim.addPauseRoutine(platformLayer);


		ModeManager manager = new ModeManager();
		cityDrawer.addMouseListener(manager);
		manager.setMode(trackBuilder);

		cityDrawer.addLayer(selectedBlock);
		
		Line red = new Line("Red",new Color(255,0,0));
    	Service redService = new Service("Red");
    	redService.setLine(red);
    	
    	Line green = new Line("Green",new Color(0,255,0));
    	Service greenService = new Service("Green");
    	greenService.setLine(green);
    	
    	Line blue = new Line("Blue",new Color(0,0,255));
    	Service blueService = new Service("Blue");
    	blueService.setLine(blue);
    	

    	sim.getCity().getLines().add(red);
    	sim.getCity().getLines().add(green);
    	sim.getCity().getLines().add(blue);
		
    	Controls controls = new Controls(sim,cityDrawer,modes,manager,serviceBuilder);

    	
    	
    	
    	//new LineFrame(sim.getCity(),serviceBuilder);
    	
    	cityDrawer.run();
    	


	}

}