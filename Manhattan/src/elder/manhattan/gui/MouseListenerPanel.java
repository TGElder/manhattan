package elder.manhattan.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MouseListenerPanel extends JPanel
{
	private final MouseListenerManager manager;
		
	public MouseListenerPanel(List<MouseListener> listeners, MouseListenerManager manager)
	{
		setName("Mode");
		setBorder(BorderFactory.createTitledBorder(getName()));
		
		setLayout(new GridBagLayout());
		
		this.manager = manager;
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridy = 0;
		
		for (MouseListener listener : listeners)
		{
			
			JButton button = new JButton(listener.toString());
			button.addActionListener
			(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent arg0)
						{
							manager.setListener(listener);
						}
					}
			);
			add(button,constraints);
			constraints.gridy ++;
		}
	}

}
