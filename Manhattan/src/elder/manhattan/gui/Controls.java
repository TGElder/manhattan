
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import elder.manhattan.City;
import elder.manhattan.Line;
import elder.manhattan.Pathfinder;
import elder.manhattan.Routine;
import elder.manhattan.Service;
import elder.manhattan.Simulation;
import elder.manhattan.SingleEdge;
import elder.manhattan.Wallet.WalletListener;
import elder.manhattan.graphics.CityDrawer;
import elder.manhattan.graphics.CityDrawerLayer;
import elder.manhattan.layers.BlockLayer;
import elder.manhattan.layers.CommuteLayer;
import elder.manhattan.layers.DijkstraLayer;
import elder.manhattan.layers.HighwayLayer;
import elder.manhattan.layers.HighwayNodeLayer;
import elder.manhattan.layers.KatherineLayer;
import elder.manhattan.layers.LocalStationsLayer;
import elder.manhattan.layers.PathfindTestLayer;
import elder.manhattan.layers.PlatformLayer;
import elder.manhattan.layers.RailwayLayer;
import elder.manhattan.layers.RoadLayer;
import elder.manhattan.layers.RoadOrRailLayer;
import elder.manhattan.layers.SelectedBlockLayer;
import elder.manhattan.layers.SelectedStationCoverage;
import elder.manhattan.layers.ServiceLayer;
import elder.manhattan.layers.StationCoverageLayer;
import elder.manhattan.layers.StationLayer;
import elder.manhattan.layers.TimeLayer;
import elder.manhattan.layers.TownLayer;
import elder.manhattan.layers.TrackLayer;
import elder.manhattan.routines.AddChildren;
import elder.manhattan.routines.AddImmigrants;
import elder.manhattan.routines.Allocate;
import elder.manhattan.routines.ComputeLocalStations;
import elder.manhattan.routines.CreateHighways;
import elder.manhattan.routines.CreateTestTubes;
import elder.manhattan.routines.CreateTowns;
import elder.manhattan.routines.Dijkstra;
import elder.manhattan.routines.GrowTowns;
import elder.manhattan.routines.OpenFields;
import elder.manhattan.routines.OpenRandomFields;
import elder.manhattan.routines.PlaceTraffic;
import elder.manhattan.routines.Populate;
import elder.manhattan.routines.UpdateStations;
import elder.manhattan.routines.UpdateTowns;



public class Controls extends JFrame implements Routine
{
	
	private final PlaceTraffic traffic;
	private final JLabel railPassengers;
	private final JLabel railDistance;
	private final JLabel ticketIncome;

	
	Controls(Simulation simulation, CityDrawer cityDrawer, List<Mode> modes, ModeManager manager, ServiceBuilder serviceBuilder, PlaceTraffic traffic)
	{
		this.traffic = traffic;
		
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
		
		JLabel money = new JLabel("�"+simulation.getCity().getWallet().getMoney(),SwingConstants.CENTER);
		add(money,constraints);
		
		simulation.getCity().getWallet().addListener(new WalletListener()
		{

			@Override
			public void onChange()
			{
				money.setText("�"+simulation.getCity().getWallet().getMoney());
			}
	
		});
		
		constraints.gridy = 1;
		railPassengers = new JLabel("0 passengers",SwingConstants.CENTER);
		add(railPassengers,constraints);
		
		constraints.gridy = 2;
		railDistance = new JLabel("0km",SwingConstants.CENTER);
		add(railDistance,constraints);
		
		constraints.gridy = 3;
		ticketIncome = new JLabel("�0",SwingConstants.CENTER);
		add(ticketIncome,constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth=2;
		constraints.gridheight=1;
		
		add(new SimulationControlPanel(simulation),constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth=2;
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
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth=1;
		constraints.gridheight=9;
		
		add(layerPanel,constraints);
				
		

		LinePanel linePanel = new LinePanel(simulation.getCity());
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth=2;
		constraints.gridheight=1;
		add(linePanel,constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.gridwidth=2;
		constraints.gridheight=1;
		
		add(new ServicePanel(simulation.getCity(),linePanel.getSelection(),serviceBuilder),constraints);
		
		pack();
		setVisible(true);
	}
	


	@Override
	public String run(Simulation simulation)
	{
		railPassengers.setText(traffic.getRailPassengers()+" passengers");
		railDistance.setText((int)(traffic.getRailDistance())+"km");
		ticketIncome.setText("�"+(int)(traffic.getRailDistance()/10));
		return null;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		
		Simulation sim = new Simulation(new City(160,160,0.5,500000),2027,500);

		CityDrawer cityDrawer = new CityDrawer(sim,1024,1024);
		
		Navigator navigator = new Navigator(cityDrawer);
		
		
		Dijkstra dijkstra = new Dijkstra(sim.getCity().getRailwayNodes());	

		
		
		HoverSelector selector = new HoverSelector(sim);
		cityDrawer.addMouseListener(selector);
		
		sim.run(new CreateTowns("uk_towns_and_counties.csv"),false);
		sim.run(new UpdateTowns(),false);
		sim.run(new OpenRandomFields(0.01),false);
		sim.run(new Populate(1000),false);
		sim.run(new CreateHighways(),false);
		//sim.run(new CreateTestTubes(),false);

		Dijkstra roadDijkstra = new Dijkstra(sim.getCity().getHighwayNodes());

		Pathfinder pathfinder = new Pathfinder(roadDijkstra,dijkstra,1);
		
		sim.run(roadDijkstra,false);

		
		TrackBuilder trackBuilder = new TrackBuilder(sim.getCity(),10000,100000,10000);
		selector.getSelectedBlock().addListener(trackBuilder);
		
		ServiceBuilder serviceBuilder = new ServiceBuilder(sim.getCity());
		selector.getSelectedBlock().addListener(serviceBuilder);
		
		StationBuilder stationBuilder = new StationBuilder(sim.getCity(),roadDijkstra,1,10,20000,40000,5000);
		selector.getSelectedBlock().addListener(stationBuilder);
		
		sim.addRoutine(new OpenFields(2.0/52.0));
		
		
		sim.addRoutine(new UpdateStations());
		
		//sim.addRoutine(new ComputeTubeways());

		sim.addRoutine(dijkstra);
		sim.addRoutine(new ComputeLocalStations(dijkstra,roadDijkstra,10));
		sim.addRoutine(new Allocate(pathfinder));
		PlaceTraffic placeTraffic = new PlaceTraffic(pathfinder);
		sim.addRoutine(placeTraffic);
		sim.addRoutine(new UpdateTowns());
		sim.addRoutine(new GrowTowns());


		sim.addRoutine(new AddImmigrants(50));
		sim.addRoutine(new AddChildren(50));
		
				
		
		
		
		
		
		BlockLayer blockLayer = new BlockLayer();
		cityDrawer.addLayer(blockLayer);
		
		
		
		// LAYERS
		TrackLayer trackLayer = new TrackLayer();
		RoadLayer footLayer = new RoadLayer(placeTraffic,"Foot Traffic",SingleEdge.FOOT);
		footLayer.disable();
		
		RoadLayer carLayer = new RoadLayer(placeTraffic,"Car Traffic",SingleEdge.CAR);
		carLayer.disable();
		
		RoadLayer busLayer = new RoadLayer(placeTraffic,"Bus Traffic",SingleEdge.BUS);
		busLayer.disable();
		
		StationLayer stationLayer = new StationLayer();
		
		PlatformLayer platformLayer = new PlatformLayer();
		StationCoverageLayer stationCoverageLayer = new StationCoverageLayer(roadDijkstra,1,10);
		HighwayNodeLayer highwayNodes = new HighwayNodeLayer();
		highwayNodes.disable();
		
		KatherineLayer katherineLayer = new KatherineLayer();
		cityDrawer.addLayer(katherineLayer);
		katherineLayer.disable();
		
		SelectedBlockLayer selectedBlock = new SelectedBlockLayer();
		selector.getSelectedBlock().addListener(selectedBlock);
		
		// MOUSE LAYERS
		LocalStationsLayer localStations = new LocalStationsLayer();
		localStations.disable();
		selector.getSelectedBlock().addListener(localStations);
		
		SelectedStationCoverage selectedStationCoverage = new SelectedStationCoverage(sim.getCity());
		selectedStationCoverage.disable();
		selector.getSelectedBlock().addListener(selectedStationCoverage);

		HighwayLayer highwayLayer = new HighwayLayer();
		highwayLayer.disable();
		selector.getSelectedBlock().addListener(highwayLayer);
		
		RailwayLayer railwayLayer = new RailwayLayer();
		railwayLayer.disable();
		selector.getSelectedBlock().addListener(railwayLayer);

		DijkstraLayer dijkstraLayer = new DijkstraLayer(dijkstra);
		selector.getSelectedBlock().addListener(dijkstraLayer);
		cityDrawer.addLayer(dijkstraLayer);
		
		CommuteLayer commuteLayer = new CommuteLayer(sim.getCity(),pathfinder,placeTraffic);
		commuteLayer.disable();
		selector.getSelectedBlock().addListener(commuteLayer);
		cityDrawer.addLayer(commuteLayer);
	
		
		
		
		PathfindTestLayer pathfindTestLayer = new PathfindTestLayer(dijkstra);
		selector.getSelectedBlock().addListener(pathfindTestLayer);
		cityDrawer.addLayer(pathfindTestLayer);
		pathfindTestLayer.disable();
		
		
		
		TimeLayer timeLayer = new TimeLayer(sim.getCity(),pathfinder);
		selector.getSelectedBlock().addListener(timeLayer);
		cityDrawer.addLayer(timeLayer);
		timeLayer.disable();
		
		RoadOrRailLayer roadOrRail = new RoadOrRailLayer(sim.getCity(),pathfinder);
		selector.getSelectedBlock().addListener(roadOrRail);
		cityDrawer.addLayer(roadOrRail);
		roadOrRail.disable();
		
		ServiceLayer serviceLayer = new ServiceLayer(placeTraffic);
		
		TownLayer townLayer = new TownLayer();
		
		cityDrawer.addLayer(stationCoverageLayer);
		cityDrawer.addLayer(trackLayer);
		cityDrawer.addLayer(footLayer);
		cityDrawer.addLayer(carLayer);
		cityDrawer.addLayer(busLayer);
		cityDrawer.addLayer(serviceLayer);
		cityDrawer.addLayer(stationLayer);
		cityDrawer.addLayer(platformLayer);
		cityDrawer.addLayer(highwayNodes);

		platformLayer.disable();

		
	
		
		cityDrawer.addLayer(trackBuilder);
		cityDrawer.addLayer(stationBuilder);
		cityDrawer.addLayer(serviceBuilder);


		sim.addRoutine(trackBuilder);
		sim.addRoutine(stationBuilder);
		sim.addRoutine(serviceBuilder);


		sim.addRoutine(blockLayer);
		sim.addRoutine(stationCoverageLayer);
		sim.addRoutine(trackLayer);
		sim.addRoutine(footLayer);
		sim.addRoutine(carLayer);
		sim.addRoutine(busLayer);
		sim.addRoutine(serviceLayer);
		sim.addRoutine(stationLayer);
		sim.addRoutine(platformLayer);
		sim.addRoutine(highwayNodes);

		
		sim.addRoutine(katherineLayer);
		sim.addRoutine(townLayer);
		

		List<Mode> modes = new ArrayList<Mode> ();
		modes.add(trackBuilder);
		modes.add(stationBuilder);
		modes.add(serviceBuilder);
		
		
		sim.addPauseRoutine(trackBuilder);
		sim.addPauseRoutine(stationBuilder);
		sim.addPauseRoutine(serviceBuilder);
		
		sim.addPauseRoutine(blockLayer);
		sim.addPauseRoutine(stationCoverageLayer);
		sim.addPauseRoutine(trackLayer);
		sim.addPauseRoutine(footLayer);
		sim.addPauseRoutine(carLayer);
		sim.addPauseRoutine(busLayer);
		sim.addPauseRoutine(serviceLayer);
		sim.addPauseRoutine(stationLayer);
		sim.addPauseRoutine(platformLayer);
		sim.addPauseRoutine(highwayNodes);
		sim.addPauseRoutine(townLayer);


		ModeManager manager = new ModeManager();
		cityDrawer.addMouseListener(manager);
		manager.setMode(trackBuilder);

		cityDrawer.addLayer(localStations);
		cityDrawer.addLayer(selectedStationCoverage);
		cityDrawer.addLayer(highwayLayer);
		cityDrawer.addLayer(railwayLayer);
		cityDrawer.addLayer(townLayer);
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
		
    	
    	
    	Controls controls = new Controls(sim,cityDrawer,modes,manager,serviceBuilder,placeTraffic);

    	sim.addRoutine(controls);
    	sim.addPauseRoutine(controls);
    	
    	
    	//new LineFrame(sim.getCity(),serviceBuilder);
    	
    	Thread thread = new Thread(sim);
		thread.start();
    	
    	cityDrawer.run();
    	


	}

}