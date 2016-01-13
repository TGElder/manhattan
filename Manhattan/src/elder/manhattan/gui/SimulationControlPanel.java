package elder.manhattan.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import elder.manhattan.Simulation;


public class SimulationControlPanel extends JPanel
{

	private Simulation sim;
	private Thread thread;
	private JButton stop;
	private JButton stepButton;
	private JSpinner iterationsSpinner;
	private SpinnerNumberModel spinnerNumberModel;
	
	public SimulationControlPanel(Simulation simulation)
	{
		sim = simulation;
		setName("Simulation");
		setBorder(BorderFactory.createTitledBorder(getName()));

		thread = new Thread(simulation);
		thread.start();
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		stepButton = new JButton("Go");
		spinnerNumberModel = new SpinnerNumberModel(1000000,1,1000000,1);
		iterationsSpinner = new JSpinner(spinnerNumberModel);
		stepButton.addActionListener
		(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						sim.setLimit(spinnerNumberModel.getNumber().intValue());
						sim.setPause(false);
					}
				}
		);
		
		stop = new JButton("Pause");
		stop.addActionListener
		(
				new ActionListener()
				{
					
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						sim.setPause(!sim.getPause());
					}
					
				}
		);
		
		
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth= 1;
		add(stepButton,constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth= 1;
		add(stop,constraints);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 2;
		add(iterationsSpinner,constraints);
		
		
	}


	
	

}
