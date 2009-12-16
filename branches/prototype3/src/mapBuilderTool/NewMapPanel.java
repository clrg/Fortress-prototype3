package mapBuilderTool;

import gameCore.Map;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class NewMapPanel extends JPanel
{
	// Pointless variable that gives a warning if I remove it
	private static final long serialVersionUID = 1L;
	
	private static JButton BtnNewGame;
	private static JLabel LblX;
	private static JLabel LblY;
	private static JSpinner SpinX;
	private static JSpinner SpinY;
	private static JTextArea TxtMapName;
	private static JLabel LblName;
	private static JLabel LblError;
	
	public static void newMap(final MainPanel main)
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
                	// create a map
                	Map newMap = new Map(mapName, x, y, "Grass"); //
	                main.currentMap = newMap;
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
