package mapBuilderTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class LaunchGui 
{
	private static JFrame StartFrame;
	public static MainPanel Main;
	private static JMenuBar MenuBar;
	private static JMenu MenuMap;
	private static JMenu MenuView;
	private static JMenuItem MINewGame;
	private static JCheckBoxMenuItem cbGrid;
	private static JCheckBoxMenuItem cbCoords;
	
	public static void main(String[] args) 
	{	
		SwingUtilities.invokeLater(new Runnable() 
		{
            public void run() 
            {
            	startUp();
            }
        });
	}
	private static void startUp()
	{
	    // Create a JFrame
		StartFrame = new JFrame();
	    StartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		StartFrame.setSize(800,530); 
		StartFrame.setVisible(true);
		StartFrame.setResizable(false);
		StartFrame.setTitle("Fortress Map Builder Alpha");
		
		// Create the MenuItems
		MINewGame = new JMenuItem("New Map");
		
		// Create the checkbox to turn gridview on/off
		cbGrid = new JCheckBoxMenuItem("Gridview on");
	    cbGrid.setSelected(true);
	    
	    // Create the checkbox to turn coordinates on/off
	    cbCoords = new JCheckBoxMenuItem("Coordinates on");
	    cbCoords.setSelected(true);
	    
	    
	    // Creating the menu's
		MenuMap = new JMenu("Map");
		MenuMap.add(MINewGame);
		MenuView = new JMenu("View");
		MenuView.add(cbGrid);
		MenuView.add(cbCoords);
		
		// The bar containing the menu's
		MenuBar = new JMenuBar();
		MenuBar.add(MenuMap);
		MenuBar.add(MenuView);
		
		
		Main = new MainPanel(true, true);
		
		StartFrame.add(Main);
		StartFrame.setJMenuBar(MenuBar);
		
		
		// Listens on the menu items
		ActionListener actionlistener = new ActionListener(  ) {
	        public void actionPerformed(ActionEvent e) {
	            try 
	            { 
	            	// Checks what menu item has been clicked on
	            	if (e.getActionCommand() == cbGrid.getText())
	            	{
	            		// If gridview is on turn it off, and turn it on if it's turned off.
	            		if (Main.getGridView() == true)
	            		{
	            			Main.setGridView(false);
	            		}
	            		else if(Main.getGridView() == false)
	            		{
	            			Main.setGridView(true);
	            		}
	            	}
	            	else if(e.getActionCommand() == cbCoords.getText())
	            	{
	            		// If coordinates are on turn them off, and the other way round.
	            		if (Main.getCoordinates() == true)
	            		{
	            			Main.setCoordinates(false);
	            		}
	            		else if(Main.getCoordinates() == false)
	            		{
	            			Main.setCoordinates(true);
	            		}
	            	}
	            	else if (e.getActionCommand() == MINewGame.getText())
	            	{
	            		NewMapPanel.newMap(Main);
	            	}
	            } 
	            catch (Exception ex) { ex.printStackTrace(  ); }
	        }
	    };
	    cbGrid.addActionListener(actionlistener);
	    cbCoords.addActionListener(actionlistener);
	    MINewGame.addActionListener(actionlistener);
	}
}
