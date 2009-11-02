package launcher;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import classes.Map;
import classes.Unit;

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
	private static JButton BtnNewGame;
	private static JLabel LblX;
	private static JLabel LblY;
	private static JSpinner SpinX;
	private static JSpinner SpinY;
	private static JTextArea TxtMapName;
	private static JLabel LblName;
	private static JLabel LblError;
	
	
	
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
                		newMap();
                	}
                } 
                catch (Exception ex) { ex.printStackTrace(  ); }
            }
        };
        cbGrid.addActionListener(actionlistener);
        cbCoords.addActionListener(actionlistener);
        MINewGame.addActionListener(actionlistener);
	}
	
	private static void newMap()
	{
		// Create a JFrame
		final JFrame popupFrame = new JFrame();
		popupFrame.setSize(300,300); 
		popupFrame.setVisible(true);
		popupFrame.setResizable(false);
		popupFrame.setTitle("New Map");
		
		JPanel popupPanel = new JPanel();
		popupPanel.setLayout(null);
		popupFrame.add(popupPanel);
		
		// New game popup controls
		TxtMapName = new JTextArea(); 
		TxtMapName.setLocation(45, 5);
		TxtMapName.setSize(80, 20);
		TxtMapName.setBorder(BorderFactory.createLineBorder(Color.black));
		
		LblName = new JLabel();
		LblName.setLocation(5,10);
		LblName.setText("Name:");
		LblName.setSize(40,15);
		
		LblX = new JLabel();
		LblX.setLocation(5,36);
		LblX.setText("Length");
		LblX.setSize(40,15);
		
		LblY = new JLabel();
		LblY.setLocation(5,60);
		LblY.setText("Width");
		LblY.setSize(40,15);
		
		SpinX = new JSpinner();
		SpinX.setLocation(45,35);
		SpinX.setSize(80,15);
		SpinX.setBorder(BorderFactory.createLineBorder(Color.black));
		SpinX.setValue(8);
		
		SpinY = new JSpinner();
		SpinY.setLocation(45,60);
		SpinY.setSize(80,15);
		SpinY.setBorder(BorderFactory.createLineBorder(Color.black));
		SpinY.setValue(8);
		
		BtnNewGame = new JButton();
		BtnNewGame.setLocation(5, 80);
		BtnNewGame.setSize(125,20);
		BtnNewGame.setText("Create map");
		
		LblError = new JLabel();
		LblError.setVisible(false);
		LblError.setForeground(Color.red);
		LblError.setLocation(5,100);
		LblError.setSize(200,20);
		
		popupPanel.add(BtnNewGame);
		popupPanel.add(LblX);
		popupPanel.add(LblY);
		popupPanel.add(SpinX);
		popupPanel.add(SpinY);
		popupPanel.add(LblName);
		popupPanel.add(TxtMapName);
		popupPanel.add(LblError);
		
		
		BtnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) 
            {
            	// need to build a check for valid characters
            	String mapName = TxtMapName.getText(); 
            	// Check if valid coordinates are being entered
            	int x = Integer.parseInt(SpinX.getValue().toString());
            	int y = Integer.parseInt(SpinY.getValue().toString());
                if ((x > 1) && (y > 1))
                {
                	Map newMap = new Map(mapName, x, y, "Grass"); //
                	Main.setCurrentMap(newMap);
                	List<Unit> myUnits = newMap.getMapUnits();
                	Unit myUnit = new Unit(350, 75, "Archer",Unit.Run);
                	myUnits.add(myUnit);
                	newMap.SetMapUnits(myUnits);
                	popupFrame.dispose();
                }
                else
                {
                	LblError.setVisible(true);
                	LblError.setText("Please enter valid numbers.");
                }
            }});
	}
}
