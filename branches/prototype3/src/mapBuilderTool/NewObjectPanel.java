package mapBuilderTool;

import gameCore.Building;
import gameCore.Map;
import gameCore.Unit;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

public class NewObjectPanel extends JPanel implements ActionListener
{
	// Pointless variable that gives a warning if I remove it
	private static final long serialVersionUID = 1L;
	
	private MainPanel main;
	private JLabel LblError;
	private JSpinner SpinX;
	private JSpinner SpinY;
	private JSpinner SpinHeight;
	private JList listObjects;
	
	public NewObjectPanel(MainPanel main)
	{
		this.main = main;
		this.setLocation(550,370);
		this.setSize(240,105);
		this.setLayout(null);
		this.setOpaque(false);
		this.setBorder(new TitledBorder("New objects"));
		
		// PanelNewObject controls
		JLabel LblX = new JLabel();
		LblX.setLocation(5,18);
		LblX.setText("X: ");
		LblX.setSize(20,10);
		
		JLabel LblY = new JLabel();
		LblY.setLocation(5,33);
		LblY.setText("Y: ");
		LblY.setSize(20,10);
		
		JLabel LblHeight = new JLabel();
		LblHeight.setLocation(5,48);
		LblHeight.setText("H:");
		LblHeight.setSize(20,10);
		
		LblError = new JLabel();
		LblError.setLocation(5,88);
		LblError.setSize(230,15);
		LblError.setForeground(Color.RED);
		LblError.setVisible(false);
		LblError.setText("Fill in all fields.");
		
		SpinX = new JSpinner();
		SpinX.setLocation(20,16);
		SpinX.setSize(50,15);
		SpinX.setBorder(BorderFactory.createLineBorder(Color.black));
		SpinX.setValue(1);
		
		SpinY = new JSpinner();
		SpinY.setLocation(20,31);
		SpinY.setSize(50,15);
		SpinY.setBorder(BorderFactory.createLineBorder(Color.black));
		SpinY.setValue(1);
		
		SpinHeight = new JSpinner();
		SpinHeight.setLocation(20,46);
		SpinHeight.setSize(50,15);
		SpinHeight.setBorder(BorderFactory.createLineBorder(Color.black));
		SpinHeight.setValue(0);
		
		String[] listItems = {"Archer", "Tower"};
		listObjects = new JList(listItems);
		listObjects.setLocation(110,10);
		listObjects.setSize(120, 76);
		listObjects.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JButton BtnAddFigure = new JButton();
		BtnAddFigure.setLocation(5, 65);
		BtnAddFigure.setSize(100,20);
		BtnAddFigure.setText("Add object");
		BtnAddFigure.addActionListener(this);
		
		this.add(LblX);
		this.add(LblY);
		this.add(LblHeight);
		this.add(LblError);
		this.add(SpinX);
		this.add(SpinY);
		this.add(SpinHeight);
		this.add(BtnAddFigure);
		this.add(listObjects);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Check if there's already a map
		if(main.currentMap != null)
		{
			Map tempMap = main.currentMap;
			
			// read the coordinates and max coordinates out
			int x = Integer.parseInt(SpinX.getValue().toString());
			int y = Integer.parseInt(SpinY.getValue().toString());
			// It's not really the height of the tower, it's the amount of middle pieces (towerstem.png)
			int height = Integer.parseInt(SpinHeight.getValue().toString());
			// get the length and width of the map -1 because the map starts at 0,0 instead of 1,1
			int maxX = tempMap.getMapTiles().get(0).size() - 1;
			int maxY = tempMap.getMapTiles().size() - 1;

			
			// check if the input is valid (bigger than zero, smaller then the max
			if(((x>=0&&x<=maxX)&&(y>=0&&y<=maxY))&&((height>-1)&&(height<10)))
			{
				// check if there is already a mapobject on the tile
				if(tempMap.getMapTiles().get(y).get(x).mapobject.type == "nothing")
				{
					// Checks what object is selected in the JList
					if(listObjects.getSelectedValue() == "Archer")
					{   
					    // Create the archer
					    Unit archer = new Unit(x,y);
					    
						// Add the mapobject to a tile
						tempMap.getMapTiles().get(y).get(x).setMapobject(archer);
					}
					else if(listObjects.getSelectedValue() == "Tower")
					{   
					    // Create the building
					    Building tower = new Building(x,y,height);
					    
					    // Add the mapobject to a tile
					    tempMap.getMapTiles().get(y).get(x).setMapobject(tower);
					}
					// Update the map
					main.currentMap = tempMap;
				}
				else
				{
					LblError.setVisible(true);
					LblError.setText("There is already a mapobject on this tile.");
				}
			}
			else
			{
				LblError.setVisible(true);
				LblError.setText("Fill in valid coordinates");
			}
		}
		else
		{
			LblError.setVisible(true);
			LblError.setText("Create or load a map first.");
		}
	}
}
