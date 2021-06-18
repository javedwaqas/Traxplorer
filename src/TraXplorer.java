/* ------------------------------------------------------------------
 * TraXplorer.java
 * 
 * Created 2008-10-29 by Niklas Elmqvist.
 * ------------------------------------------------------------------
 */

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import edu.umd.cs.piccolox.PFrame;

public class TraXplorer implements ActionListener {

	private PFrame frame;
	private TrackCreator tc;
	private TrackAdder ta;
	float x[] = new float [10000];
	float y[] = new float [10000];
	AWTEvent ee; 
	
	public TraXplorer(String name) {

		// Create the Java frame
    	frame = new LayoutManager();
    	frame.setPreferredSize(new Dimension(1278, 785));
//    	frame.setBounds(150, 0, frame.getWidth(), frame.getHeight());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	// Create the menu
    	buildMenuBar();
    	/*
    	for (int i=0; i< x.length; i++) {
			x[i] = (float) (i*Math.PI/180);
			y[i] = (float) Math.sin(x[i]);
		}
    	
    	((LayoutManager) frame).draw(x, y);
    	
    	*/
    	
    	// Configure the frame
    	frame.pack();
    	
    	// Build the scene graph
    	GUIUtils.centerOnPrimaryScreen(frame);
    	//System.err.println("column value is: " + tc.getValueColumn().getIntValue(2));
   	}
	
	private void buildMenuBar() { 
    	JMenu menu;
    	JMenuBar menuBar;
    	JMenuItem menuItem;
    	
    	menuBar = new JMenuBar();
    	
    	menu = new JMenu("File");
    	menu.setMnemonic(KeyEvent.VK_F);

    	menuItem = new JMenuItem("New");
    	menuItem.setMnemonic(KeyEvent.VK_N);
    	menuItem.addActionListener(this);
    	menu.add(menuItem);
    	
    	menuItem = new JMenuItem("Add track");
    	menuItem.setMnemonic(KeyEvent.VK_A);
    	menuItem.addActionListener(this);
    	menu.add(menuItem);
    	
    	menuItem = new JMenuItem("Save session");
    	menuItem.setMnemonic(KeyEvent.VK_S);
    	menuItem.addActionListener(this);
    	menu.add(menuItem);
    	
    	menu.addSeparator();

    	menuItem = new JMenuItem("Exit");
    	menuItem.setMnemonic(KeyEvent.VK_X);
    	menuItem.addActionListener(this);
    	menu.add(menuItem);

    	menuBar.add(menu);
    	
    	menu = new JMenu("Options");
    	menu.setMnemonic(KeyEvent.VK_O);

    	  	
    	JRadioButtonMenuItem menuButton = new JRadioButtonMenuItem("Link Y-Scale");
    	menuButton.setMnemonic(KeyEvent.VK_L);
    	menuButton.setActionCommand("Link Y-Scale");
    	menuButton.addActionListener(this);
    	menu.add(menuButton);
    	
    	JRadioButtonMenuItem menuButton1 = new JRadioButtonMenuItem("Fill Graph");
    	menuButton1.setMnemonic(KeyEvent.VK_F);
    	menuButton1.setActionCommand("Fill Graph");
    	menuButton1.addActionListener(this);
    	menu.add(menuButton1);
    	
      	menuBar.add(menu);

    	menu = new JMenu("Help");
    	menu.setMnemonic(KeyEvent.VK_H);

    	menuItem = new JMenuItem("About");
    	menuItem.setMnemonic(KeyEvent.VK_A);
    	menuItem.addActionListener(this);
    	menu.add(menuItem);

    	menuBar.add(menu);

    	frame.setJMenuBar(menuBar);		
	}

	public void run() {
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (((ActionEvent) e).getActionCommand().equals("New")) {
			JFileChooser fileChooser = new JFileChooser(".");
	        int ret = fileChooser.showOpenDialog(null);
	        if (ret == JFileChooser.APPROVE_OPTION) {
	        	tc = new TrackCreator(frame, fileChooser.getSelectedFile(), frame);
	        	//System.err.println("column value is: " + tc.getValueColumn().getIntValue(2));
	            boolean success = tc.showOpenDialog();
	            if (success) {
	            	System.err.println("Accepted file " + fileChooser.getSelectedFile());
	            }
	        }
		}
		else		
			if (((ActionEvent) e).getActionCommand().equals("Add track")) {
				JFileChooser fileChooser = new JFileChooser(".");
		        int ret = fileChooser.showOpenDialog(null);
		        if (ret == JFileChooser.APPROVE_OPTION) {
		        	ta = new TrackAdder(frame, fileChooser.getSelectedFile(), frame);
		        	
		            boolean success = ta.showOpenDialog();
		            if (success) {
		            	System.err.println("Accepted file " + fileChooser.getSelectedFile());
		            }
		        }
			}
			else 		
				if (((ActionEvent) e).getActionCommand().equals("Link Y-Scale")) {
					JRadioButtonMenuItem a = (JRadioButtonMenuItem) e.getSource(); 
					if (a.isSelected())
					((LayoutManager)frame).aNode[0][0].reDrawLinkLayer(true);
					else
						((LayoutManager)frame).aNode[0][0].reDrawLinkLayer(false);
				}
				else 		
					if (((ActionEvent) e).getActionCommand().equals("Fill Graph")) {
						JRadioButtonMenuItem a = (JRadioButtonMenuItem) e.getSource(); 
						if (a.isSelected())
						((LayoutManager)frame).fillGraph();
						else
							((LayoutManager)frame).notFillGraph();
					}
					else { 
						System.err.println("Unhandled event: " + e);			
					}
				
	}
			
	public static void main(String[] args) {
		TraXplorer app = new TraXplorer("TraXplorer version 0.1");
		app.run();	
	}

}
