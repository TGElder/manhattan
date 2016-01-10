package elder.manhattan.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import elder.manhattan.City;

public class LineFrame extends JFrame
{

	public LineFrame(City city,ServiceBuilder serviceBuilder)
	{
		
		setTitle("Lines");
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		LinePanel linePanel = new LinePanel(city);
		add(linePanel);
		
		constraints.gridx = 1;
		
		add(new ServicePanel(city,linePanel.getSelection(),serviceBuilder));

		
		pack();
		setVisible(true);
	}
	
}
