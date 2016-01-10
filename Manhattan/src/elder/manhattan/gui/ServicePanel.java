package elder.manhattan.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import elder.manhattan.City;
import elder.manhattan.Line;
import elder.manhattan.Selection;
import elder.manhattan.SelectionListener;
import elder.manhattan.Service;

public class ServicePanel extends JPanel
{
	
	private final City city;
	
	private final Selection<List<Service>> selection;

			
	public ServicePanel(City city, Selection<List<Line>> lineSelection, ServiceBuilder serviceBuilder)
	{				
		this.city = city;
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		setName("Services");
		setBorder(BorderFactory.createTitledBorder(getName()));
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth=3;
		constraints.gridheight=1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		final ServiceList serviceList = new ServiceList();
		add(new JScrollPane(serviceList),constraints);
		
		JButton addServiceButton = new JButton("New Service");
		addServiceButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Service newService = new Service("New Service");
				new ServiceEditor(newService,serviceList,city.getLines(),serviceBuilder);
				serviceList.addService(newService);
				
			}});
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weighty = 0;
		add(addServiceButton,constraints);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (Service service : serviceList.getSelection().getSelection())
				{
					new ServiceEditor(service,serviceList,city.getLines(),serviceBuilder);
				}
				
			}});
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weighty = 0;
		add(editButton,constraints);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (Service service : serviceList.getSelection().getSelection())
				{
					service.setLine(null);
					serviceList.removeService(service);;
				}
				
			}});
		
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.weighty = 0;
		add(deleteButton,constraints);
		
		lineSelection.addListener(serviceList);
		
		selection = serviceList.getSelection();

	}
	
	private class ServiceList extends JList<Service> implements SelectionListener<List<Line>>,ListSelectionListener
	{
		private DefaultListModel<Service> model;

		private final Selection<List<Service>> selection;
		
		private List<Line> lines;
		
		ServiceList()
		{
			
			this.setVisibleRowCount(8);
			
			this.selection = new Selection<List<Service>> ();
			
			model = new DefaultListModel<Service>();
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
		
		void addService(Service service)
		{
			model.addElement(service);
		}
		
		void removeService(Service service)
		{
			model.removeElement(service);
		}
		
		Selection<List<Service>> getSelection()
		{
			return selection;
		}
		
		@Override
		public void onSelect(List<Line> selection)
		{
			lines = selection;
			refresh();
		}
		
		public void refresh()
		{
			model.removeAllElements();
			
			for (Line line : lines)
			{
				for (Service service : line.getServices())
				{
					model.addElement(service);
				}
			}
			
			this.setSelectionInterval(0, getModel().getSize()-1);
		}
	}
	
	private class ServiceEditor extends JFrame
	{
		
		public ServiceEditor(Service service, final ServiceList serviceList, List<Line> lines, ServiceBuilder serviceBuilder)
		{	
			setTitle("Edit Service");
			
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
			textField.setText(service.getName());
			textField.selectAll();
			constraints.gridy = 0;
			constraints.gridwidth=2;
			constraints.weighty = 0;
			add(textField,constraints);
			
			JComboBox lineChooser = new JComboBox(lines.toArray());
			if (service.getLine()!=null)
			{
				lineChooser.setSelectedItem(service.getLine());
			}
			
			constraints.gridy = 1;
			constraints.gridwidth=2;
			constraints.weighty = 1;
			add(lineChooser,constraints);
			
			JButton okay = new JButton("OK");
			okay.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e)
				{
					service.setName(textField.getText());
					service.setLine((Line)lineChooser.getSelectedItem());
					serviceList.refresh();
					dispose();
				}});
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.gridwidth=1;
			constraints.weighty = 0;
			add(okay,constraints);
			
			JButton build = new JButton("Build");
			build.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e)
				{
					serviceBuilder.setService(service);
					dispose();
				}});
			constraints.gridx = 1;
			constraints.gridy = 2;
			constraints.gridwidth=1;
			constraints.weighty = 0;
			add(build,constraints);
			
			JButton cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}});
			constraints.gridx = 2;
			constraints.gridy = 2;
			constraints.gridwidth=1;
			constraints.weighty = 0;
			add(cancel,constraints);
			
			pack();
			setVisible(true);

		}
		

	}

	
}


