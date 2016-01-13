package elder.manhattan.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ModePanel extends JPanel
{
	private final ModeManager manager;
		
	public ModePanel(List<Mode> modes, ModeManager manager)
	{
		setName("Mode");
		setBorder(BorderFactory.createTitledBorder(getName()));
		
		setLayout(new GridBagLayout());
		
		this.manager = manager;
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridy = 0;
		
		for (Mode mode : modes)
		{
			if (mode!=manager.getMode())
			{
				mode.disable();
			}
			
			JButton button = new JButton(mode.toString());
			button.addActionListener
			(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent arg0)
						{
							manager.setMode(mode);
						}
					}
			);
			add(button,constraints);
			constraints.gridy ++;
		}
	}

}
