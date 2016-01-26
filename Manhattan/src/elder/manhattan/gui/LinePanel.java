package elder.manhattan.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import elder.manhattan.City;
import elder.manhattan.Line;
import elder.manhattan.Selection;

public class LinePanel extends JPanel 
{
	
	private final City city;
	private final Selection<List<Line>> selection;
		
	public LinePanel(City city)
	{		
		this.city = city;
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		setName("Lines");
		setBorder(BorderFactory.createTitledBorder(getName()));
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth=3;
		constraints.gridheight=1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		final LineList lineList = new LineList();
		add(new JScrollPane(lineList),constraints);
		
		JButton addLineButton = new JButton("New Line");
		addLineButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Line newLine = new Line("New Line",new Color(0f,0f,0f));
				city.getLines().add(newLine);
				lineList.addLine(newLine);
				new LineEditor(newLine,lineList);
				
			}});
		
		for (Line line : city.getLines())
		{
			lineList.addLine(line);
		}
		
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weighty = 0;
		add(addLineButton,constraints);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (Line line : lineList.getSelection().getSelection())
				{
					new LineEditor(line,lineList);
				}
				
			}});
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weighty = 0;
		add(editButton,constraints);
		
		JButton removeLineButton = new JButton("Delete");
		removeLineButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (Line line : lineList.getSelection().getSelection())
				{
					if (line.getServices().isEmpty())
					{
						city.getLines().remove(line);
						lineList.removeLine(line);
					}
					else
					{
						System.out.println("Remove services from this line before deleting it.");
					}
				}
				
			}});
		
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weighty = 0;
		add(removeLineButton,constraints);
		
		selection = lineList.getSelection();

	}
	
	public Selection<List<Line>> getSelection()
	{
		return selection;
	}
	

	private class LineList extends JList<Line> implements ListSelectionListener
	{
		private DefaultListModel<Line> model;

		private final Selection<List<Line>> selection;
		
		LineList()
		{
			
			this.setVisibleRowCount(8);
			
			this.selection = new Selection<List<Line>> ();
			
			model = new DefaultListModel<Line>();
			setModel(model);

			setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			
			addListSelectionListener(this);
			
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			if (!e.getValueIsAdjusting())
			{
				selection.setSelection(getSelectedValuesList());
			}
		}
		
		void addLine(Line line)
		{
			model.addElement(line);
		}
		
		void removeLine(Line line)
		{
			model.removeElement(line);
		}
		
		Selection<List<Line>> getSelection()
		{
			return selection;
		}
		
	}
	
	private class LineEditor extends JFrame
	{
		
		public LineEditor(Line line, final LineList lineList)
		{	
			setTitle("Edit Line");
			
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
			textField.setText(line.getName());
			textField.selectAll();
			constraints.gridy = 0;
			constraints.gridwidth=2;
			constraints.weighty = 0;
			add(textField,constraints);
			
			JColorChooser colorChooser = new JColorChooser();
			ColorSelectionModel model = colorChooser.getSelectionModel();
		    model.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent changeEvent) {
				        line.setColor(colorChooser.getColor());
				      }
				    });
			colorChooser.setColor(line.getColor());
			constraints.gridy = 1;
			constraints.gridwidth=2;
			constraints.weighty = 1;
			add(colorChooser,constraints);
			
			JButton okay = new JButton("OK");
			okay.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e)
				{
					line.setName(textField.getText());
					lineList.repaint();
					dispose();
				}});
			constraints.gridx = 0;
			constraints.gridy = 2;
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
			constraints.gridy = 2;
			constraints.gridwidth=1;
			constraints.weighty = 0;
			add(cancel,constraints);
			
			pack();
			setVisible(true);

		}
		

	}
}


