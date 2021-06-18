/* ------------------------------------------------------------------
 * TrackCreator.java
 * 
 * Created 2008-10-29 by Niklas Elmqvist.
 * ------------------------------------------------------------------
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import edu.umd.cs.piccolox.PFrame;

public class TrackCreator extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	public enum TimeAxis { Column, Rows, Interval };
	
	private boolean result = false;
	private TimeAxis timeAxis = TimeAxis.Column;
	private Table table = new BasicTable();
	
	private JTextField nameField;
	private JComboBox timeCombo, valueCombo;
	private JSpinner intervalSpinner;
	private PFrame frame;
	private float  yy[];
	private String xx[];
	private String trackName;
	
	public TrackCreator(JFrame owner, File file, PFrame f) {
		super(owner);
		frame = f;
		
		TableReader reader = new TableReader(file, table);
		reader.load();
		System.err.println("Table: " + table + ", " + table.getRowCount() + " rows.");
		
		setTitle("Add New Track");
		setPreferredSize(new Dimension(500, 300));
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		trackName = file.getName();
		int index = trackName.lastIndexOf('.');
		if (index != -1) {
			trackName = trackName.substring(0, index);
		}
		buildDialog(trackName);
		
		GUIUtils.centerOnPrimaryScreen(this);
		pack();
	}
	
	private void buildDialog(String name) {
		
		JPanel namePanel = new JPanel();
		nameField = new JTextField(name, 40);
		namePanel.add(nameField);
		namePanel.setBorder(BorderFactory.createTitledBorder("Track name"));
		
		JPanel okCancelPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		okCancelPanel.add(okButton);
		okCancelPanel.add(cancelButton);

		ArrayList<Column> timeItems = new ArrayList<Column>();
		for (int i = 0; i < table.getColumnCount(); i++) { 
			Column c = table.getColumnAt(i);
			timeItems.add(c);
		}
		timeCombo = new JComboBox(timeItems.toArray());
		timeCombo.setEditable(false);
		
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
		timePanel.setBorder(BorderFactory.createTitledBorder("Time Axis Labels"));

		ButtonGroup group = new ButtonGroup();

		JPanel panel;
		panel = new JPanel(); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		JRadioButton rbutton;
		rbutton = new JRadioButton("existing column");
		rbutton.setSelected(true);
		rbutton.addActionListener(this);
		group.add(rbutton);
		panel.add(rbutton);
		panel.add(timeCombo);
		timePanel.add(panel);

		panel = new JPanel(); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		rbutton = new JRadioButton("row numbers");
		rbutton.setSelected(true);
		rbutton.addActionListener(this);
		group.add(rbutton);
		group.add(rbutton);
		panel.add(rbutton);
		panel.add(Box.createHorizontalGlue());
		timePanel.add(panel);

		panel = new JPanel(); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		rbutton = new JRadioButton("regular intervals");
		rbutton.setSelected(true);
		rbutton.addActionListener(this);		
		intervalSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 1000.0, 0.1));
		intervalSpinner.setEnabled(false);
		group.add(rbutton);
		panel.add(rbutton);
		panel.add(intervalSpinner);
		timePanel.add(panel);
				
		JPanel valuePanel = new JPanel();
		ArrayList<Column> valueItems = new ArrayList<Column>();
		for (int i = 0; i < table.getColumnCount(); i++) { 
			Column c = table.getColumnAt(i);
			if (c.getType() == Column.Type.Integer || c.getType() == Column.Type.Real) { 
				valueItems.add(c);
			}
		}
		valueCombo = new JComboBox(valueItems.toArray());
		valueCombo.setEditable(false);
		valuePanel.add(valueCombo);
		valuePanel.setBorder(BorderFactory.createTitledBorder("Value Axis"));
		
		JPanel centerPanel = new JPanel();
		centerPanel.add(timePanel);
		centerPanel.add(valuePanel);
		
		getContentPane().add(okCancelPanel, BorderLayout.PAGE_END);		
		getContentPane().add(namePanel, BorderLayout.PAGE_START);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
	}
	
	public boolean showOpenDialog() { 
		setModal(true);
		setVisible(true);
		return result;
	}
	
	public TimeAxis getTimeAxis() { 
		return timeAxis;
	}
	
	public Column getValueColumn() { 
		return (Column) valueCombo.getSelectedItem();
	}
	
	public Column getTimeColumn() { 
		return (Column) timeCombo.getSelectedItem();
	}

	public double getTimeInterval() { 
		return (Double) intervalSpinner.getValue();
	}
	
	public String getName() {
		return nameField.getText();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			result = true;
			setVisible(false);
			
			//xx = new float [getTimeColumn().getRowCount()];//getValueColumn().getRowCount()];
			xx = new String [getTimeColumn().getRowCount()];//getValueColumn().getRowCount()];
			yy = new float [getValueColumn().getRowCount()];//getValueColumn().getRowCount()];
			
			for (int i=0; i< xx.length; i++) { //getValueColumn().getRowCount(); i++){
				
				yy[i] = (float) getValueColumn().getRealValueAt(i);
				xx[i] = getTimeColumn().getStringValueAt(i);
				//xx[i] = i;
				
			}
			((LayoutManager) frame).trackName.add(nameField.getText());
			((LayoutManager) frame).draw(xx, yy);
			
		}
		else if (e.getActionCommand().equals("Cancel")) { 
			result = false;
			setVisible(false);
		}
		else if (e.getActionCommand().equals("existing column")) {
			timeAxis = TimeAxis.Column;
			timeCombo.setEnabled(true);
			intervalSpinner.setEnabled(false);
		}
		else if (e.getActionCommand().equals("row numbers")) {
			timeAxis = TimeAxis.Rows;
			timeCombo.setEnabled(false);
			intervalSpinner.setEnabled(false);
		}
		else if (e.getActionCommand().equals("regular intervals")) {
			timeAxis = TimeAxis.Interval;
			timeCombo.setEnabled(false);
			intervalSpinner.setEnabled(true);
		}
	}
}
