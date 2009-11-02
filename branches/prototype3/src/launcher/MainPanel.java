package launcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import classes.Map;
import classes.MapObject;
import classes.MapObjectThread;
import classes.Tile;
import classes.Unit;

public class MainPanel extends JPanel implements MouseListener
{
	// Map
	private Map currentMap;
	
	// Components
	private static JPanel PanelNewObject;
	private static JPanel PanelMap;
	private static JButton BtnAddFigure;
	private static JLabel LblX;
	private static JLabel LblY;
	private static JLabel LblHeight;
	private static JLabel LblError;
	private static JSpinner SpinX;
	private static JSpinner SpinY;
	private static JSpinner SpinHeight;
	private static Image tile;
	private static Image grass;
	private static Image Selected;
	private static Image TowerBase;
	private static Image TowerStem;
	private static Image TowerTop;
	private static JList listObjects;
	
	public static int idleSprites = 35;
	private static List<Image> idle_n;
	private static List<Image> idle_nw;
	private static List<Image> idle_w;
	private static List<Image> idle_sw;
	private static List<Image> idle_s;
	private static List<Image> idle_se;
	private static List<Image> idle_e;
	private static List<Image> idle_ne;
	
	private static List<Image> run_n;
	private static List<Image> run_nw;
	private static List<Image> run_w;
	private static List<Image> run_sw;
	private static List<Image> run_s;
	private static List<Image> run_se;
	private static List<Image> run_e;
	private static List<Image> run_ne;
	
	// Variables
	private boolean gridview = true;
	private boolean coordinates = true;
	public static int runSprites = 11;
	public final int startX = 350;
	public final int startY = 10;
	public final int tilewidth = 97;
	public final int tileheight = 49;
	
	// Some getters and setters
	public boolean getGridView()
	{
		return gridview;
	}
	public void setGridView(boolean value)
	{
		gridview = value;
	}
	public Map getCurrentMap()
	{
		return currentMap;
	}
	public void setCurrentMap(Map currentMap)
	{
		this.currentMap = currentMap;
	}
	public int getIdleSprites()
	{
		return idleSprites;
	}
	public int getRunSprites()
	{
		return runSprites;
	}	
	public void setCoordinates(boolean coordinates) {
		this.coordinates = coordinates;
	}
	public boolean getCoordinates() {
		return coordinates;
	}
	
	// Constructor
	public MainPanel(boolean gridview, boolean coordinates)
	{
		this.gridview = gridview;
		this.coordinates = coordinates;
		super.setLayout(null); // Do this always or the components get a default location
		setControls();
		
		// Get the images
		getSprites();
   		
		
		addMouseListener(this);
		
		
		/* According to some random guy who spoke to me on msn, the following code 
		 * should make everything repaint all the time.  
		 */
		Timer refreshTimer = new javax.swing.Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        refreshTimer.start();
	    
	    Runnable runnable = new MapObjectThread(this);
	    Thread thread = new Thread(runnable);
	    thread.start();
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);       
		Color wit = new Color(255,255,255);
		
		int tempX = startX;
		int tempY = startY;
		
		// The background
		g.setColor(Color.BLACK);
	    g.fillRect(0,0,800,600);
	    
	    if (currentMap != null) // Check if there is a map to load
	    {
    	
			// The ownage map builder
	    	for (int y = 0; y < currentMap.getMapInfo().size(); y++)
		    {
				for (int x = 0; x < currentMap.getMapInfo().get(y).size(); x++)
				{
					if(currentMap.getMapInfo().get(y).get(x).getTerrain() == "Grass")
					{
						g.drawImage(grass,tempX,tempY, null);
						if (gridview == true)
						{
							g.drawImage(tile,tempX,tempY, null);
						}
						if (coordinates == true)
						{
							String coord = (x+1) + "," + (y+1);
							g.setColor(Color.WHITE);
							
							// the -8 and +3 are to position the coordinate in the middle of the tile
							g.drawString(coord, (tempX + (tilewidth /2)-8 ),(tempY+ (tileheight /2)+3));
							
							g.drawString("Y", 100, 100);
							g.drawString("X", 700, 100);
						}
					}
					/* Now busy to replace this, if the app works and this is still
					 * commented I've forgotten to remove it.
					if(currentMap.getMapInfo().get(y).get(x).getMapobject() != null)
					{
						MapObject currentMapObject = currentMap.getMapInfo().get(y).get(x).getMapobject();
						if(currentMapObject.getType() == "Tower")
						{
							
						}
					}*/				
					tempX += (tilewidth / 2);
					tempY += (tileheight / 2);
				}
				tempX = (startX - ((y+1) * (tilewidth / 2)));
				tempY = (startY + ((y+1) * (tileheight / 2)));
	    	}
		    		
			//draw the units
			List<Unit> myMapUnits = currentMap.getMapUnits();
				
			for (int unitcounter = 0; unitcounter < myMapUnits.size(); unitcounter++)
			{
				Unit myUnit = myMapUnits.get(unitcounter);
				
				if(myUnit.getMoveToX() != 0)
				{
					if (myUnit.getStatus() == Unit.Idle){
						myUnit.setStatus(Unit.Run);
						myUnit.setCurrentSprite(1);	
					}
					
					
					int devideby = 1;
					int rcx;
					rcx = myUnit.getMoveToX() - myUnit.getX();
					if (rcx < 0)
					{
						rcx = rcx * -1;
					}
					int rcy;
					rcy = myUnit.getMoveToY() - myUnit.getY();
					if (rcy < 0)
					{
						rcy = rcy * -1;
					}
					if(rcx < rcy){
						if (rcy > 5)
						{
							devideby = rcy / 5;
							rcy = 5;
							rcx = rcx / devideby;
						}
						if(myUnit.getX() < myUnit.getMoveToX()){
							myUnit.setX(myUnit.getX() + rcx);
						}
						else
						{
							myUnit.setX(myUnit.getX() - rcx);
						}
											
						if(myUnit.getY() < myUnit.getMoveToY()){
							myUnit.setY(myUnit.getY() +rcy);
						}
						else
						{
							myUnit.setY(myUnit.getY() -rcy);
						}						
					}
					else {
						//rcx > rcy
						if (rcx > 5)
						{
							devideby = rcx / 5;
							rcx = 5;
							rcy = rcy / devideby;
						}
						if(myUnit.getY() < myUnit.getMoveToY()){
							myUnit.setY(myUnit.getY() + rcy);
						}
						else
						{
							myUnit.setY(myUnit.getY() - rcy);
						}
						
						if(myUnit.getX() < myUnit.getMoveToX()){
							myUnit.setX(myUnit.getX() +rcx);
						}
						else
						{
							myUnit.setX(myUnit.getX() -rcx);
						}						
					}
				}
				List<Image> myImageList = GetImageList(myUnit.getStatus(), myUnit.getDirection());
				if(myUnit.getSelected()){
					g.drawImage(Selected, myUnit.getX() -18, myUnit.getY() - 10, null);
				}
				
				//Calculate direction
				int DiffX;
				DiffX = myUnit.getMoveToX() - myUnit.getX();
				if (DiffX < 0)
				{
					DiffX = DiffX * -1;
				}
				int DiffY;
				DiffY = myUnit.getMoveToY() - myUnit.getY();
				if (DiffY < 0)
				{
					DiffY = DiffY * -1;
				}
				double direction = 0;
				if (DiffX > DiffY){
					//pythagoras
					direction = Math.tanh(DiffY / DiffX);
				}
				else if (DiffY > DiffX)
				{
					direction = Math.tanh(DiffX / DiffY);
				}
				else
				{
					direction = -1;
					if(myUnit.getStatus() == Unit.Run){
						myUnit.setStatus(Unit.Idle);
						myUnit.setCurrentSprite(1);	
					}					
				}
				
				if (direction != -1)
				{
					int X = myUnit.getMoveToX() - myUnit.getX();
					int Y = myUnit.getMoveToY() - myUnit.getY();
					
					if(X > 0 && Y > 0 && DiffX < DiffY && direction >= 0 && direction <= 22.5){
						myUnit.setDirection(myUnit.North);
					}
					else if(X > 0 && Y > 0 && DiffX < DiffY && direction > 22.5 && direction <= 45){
						myUnit.setDirection(myUnit.NorthEast);
					}
					else if(X > 0 && Y > 0 && DiffX > DiffY && direction > 22.5 && direction <= 45){
						myUnit.setDirection(myUnit.NorthEast);
					}
					else if(X > 0 && Y > 0 && DiffX > DiffY && direction >= 0 && direction <= 22.5){
						myUnit.setDirection(myUnit.East);
					}
					else if(X > 0 && Y < 0 && DiffX > DiffY && direction >= 0 && direction <= 22.5){
						myUnit.setDirection(myUnit.East);
					}
					else if(X > 0 && Y < 0 && DiffX > DiffY && direction > 22.5 && direction <= 45){
						myUnit.setDirection(myUnit.SouthEast);
					}
					else if(X > 0 && Y < 0 && DiffX < DiffY && direction > 22.5 && direction <= 45){
						myUnit.setDirection(myUnit.SouthEast);
					}
					else if(X > 0 && Y < 0 && DiffX < DiffY && direction >= 0 && direction <= 22.5){
						myUnit.setDirection(myUnit.South);
					}
					else if(X < 0 && Y < 0 && DiffX < DiffY && direction >= 0 && direction <= 22.5){
						myUnit.setDirection(myUnit.South);
					}
					else if(X < 0 && Y < 0 && DiffX < DiffY && direction > 22.5 && direction <= 45){
						myUnit.setDirection(myUnit.SouthWest);
					}				
					else if(X < 0 && Y < 0 && DiffX > DiffY && direction > 22.5 && direction <= 45){
						myUnit.setDirection(myUnit.SouthWest);
					}
					else if(X < 0 && Y < 0 && DiffX > DiffY && direction >= 0 && direction <= 22.5){
						myUnit.setDirection(myUnit.West);
					}
					else if(X < 0 && Y > 0 && DiffX > DiffY && direction >= 0 && direction <= 22.5){
						myUnit.setDirection(myUnit.West);
					}
					else if(X < 0 && Y > 0 && DiffX > DiffY && direction > 22.5 && direction <= 45){
						myUnit.setDirection(myUnit.NorthWest);
					}
					else if(X < 0 && Y > 0 && DiffX < DiffY && direction > 22.5 && direction <= 45){
						myUnit.setDirection(myUnit.NorthWest);
					}
					else if(X < 0 && Y > 0 && DiffX < DiffY && direction >= 0 && direction <= 22.5){
						myUnit.setDirection(myUnit.North);
					}
				}
				// finally draw the units
				g.drawImage(myImageList.get(myUnit.getCurrentSpite() - 1), myUnit.getX() -16 ,myUnit.getY() -26, null);
				
				// draw the mapObjects
				if(currentMap.getMapObjects() != null)
				{
					for(int i = 0; i < currentMap.getMapObjects().size(); i++)
					{
						MapObject current = currentMap.getMapObjects().get(i);
						if (current.getType() == "TowerBase")
						{
							g.drawImage(TowerBase,current.getX(),current.getY(), null);
						}
						else if (current.getType() == "TowerStem")
						{
							g.drawImage(TowerStem,current.getX(),current.getY(), null);
						}
						else if (current.getType() == "TowerTop")
						{
							g.drawImage(TowerTop,current.getX(),current.getY(), null);
						}
					}
				}
			}
	    }
	}
	private void MouseClick(int Y, int X, int MouseButton)
	{
		if(currentMap != null ){
		
			List<Unit> myMapUnits = currentMap.getMapUnits();
			
			if(MouseButton == 1)
			{
				for (int unitcounter = 0; unitcounter < myMapUnits.size(); unitcounter++)
				{				
					//Check if the unit is within the bounds of the x & Y of the mouseclick
					Unit myUnit = myMapUnits.get(unitcounter);
					myUnit.setSelected(false);
					if(X > myUnit.getX() -16 &&
						X < myUnit.getX() + 16 &&
						Y > myUnit.getY() -16 &&
						Y < myUnit.getY() + 16 )
					{
						if(myUnit.getSelected())
						{
							myUnit.setSelected(false);
						}
						else
						{
							myUnit.setSelected(true);	
						}
					}			
				}
			}
			else if(MouseButton == 3)
			{
				
				Unit myUnit = new Unit(0, 0, "", "");
				boolean UnitSelected = false;
				for (int unitcounter = 0; unitcounter < myMapUnits.size(); unitcounter++)
				{				
					//Check if the unit is within the bounds of the x & Y of the mouseclick
					 myUnit = myMapUnits.get(unitcounter);
				
					if(myUnit.getSelected())
					{
						UnitSelected = true;
						break;
					}
				}
				if (UnitSelected)
				{
					int tilewidth = 97;
					int tileheight = 49;
					
					final int startX = 350 + (tilewidth/2);
					final int startY = 10 + (tileheight/2);
					
					int tempX = startX;
					int tempY = startY;
					
					double difference = -1;
					int tempdifferenceX = 0;
					int tempdifferenceY = 0;
					
					int MoveToX = 0;
					int MoveToY = 0;
					for (int y = 0; y < currentMap.getMapInfo().size(); y++)
				    {
						for (int x = 0; x < currentMap.getMapInfo().get(y).size(); x++)
						{
							tempdifferenceX = tempX - X;
							if(tempdifferenceX < 0)
							{
								tempdifferenceX = tempdifferenceX * -1;
							}
							tempdifferenceY = tempY - Y;
							if(tempdifferenceY < 0)
							{
								tempdifferenceY = tempdifferenceY * -1;
							}
							if(difference == -1)
							{
								MoveToX = tempX;
								MoveToY = tempY;
								difference = Math.sqrt((tempdifferenceX * tempdifferenceX) + (tempdifferenceY * tempdifferenceY));
							}
							else
							{
								double mydiff = Math.sqrt((tempdifferenceX * tempdifferenceX) + (tempdifferenceY * tempdifferenceY));
								if(difference > mydiff)
								{
									MoveToX = tempX;
									MoveToY = tempY;
									difference = tempdifferenceX + tempdifferenceY;
								}
							}
							tempX += (tilewidth / 2);
							tempY += (tileheight / 2);
						}
						tempX = (startX - ((y+1) * (tilewidth / 2)));
						tempY = (startY + ((y+1) * (tileheight / 2)));
				    }
					
					System.out.println("move to x:" + MoveToX);
					System.out.println("move to y:" + MoveToY);
					myUnit.setMoveToX(MoveToX);
					myUnit.setMoveToY(MoveToY);
				}
			}
		}
	}
	public List<Image> GetImageList(String Status, String Direction)
	{
		List<Image> ImageList = new ArrayList<Image>();
		if(Status == Unit.Run)
		{
			if(Direction == Unit.NorthWest)
				ImageList = run_nw;
			else if(Direction == Unit.North)
				ImageList = run_n;			
			else if(Direction == Unit.NorthEast)
				ImageList = run_ne;
			else if(Direction == Unit.East)
				ImageList = run_e;
			else if(Direction == Unit.SouthEast)
				ImageList = run_se;
			else if(Direction == Unit.South)
				ImageList = run_s;
			else if(Direction == Unit.SouthWest)
				ImageList = run_sw;
			else if(Direction == Unit.West)
				ImageList = run_w;
		}
		else if(Status == Unit.Idle)
		{
			if(Direction == Unit.NorthWest)
				ImageList = idle_nw;
			else if(Direction == Unit.North)
				ImageList = idle_n;			
			else if(Direction == Unit.NorthEast)
				ImageList = idle_ne;
			else if(Direction == Unit.East)
				ImageList = idle_e;
			else if(Direction == Unit.SouthEast)
				ImageList = idle_se;
			else if(Direction == Unit.South)
				ImageList = idle_s;
			else if(Direction == Unit.SouthWest)
				ImageList = idle_sw;
			else if(Direction == Unit.West)
				ImageList = idle_w;
		}		
		return ImageList;
	}
	public void getSprites()
	{
		try 
        {
            tile = ImageIO.read(new File("images/grid.png"));
            grass = ImageIO.read(new File("images/grass.gif"));
            Selected = ImageIO.read(new File("images/SelectRect.gif"));
            TowerBase = ImageIO.read(new File("images/squaretower/base.png"));
            TowerStem = ImageIO.read(new File("images/squaretower/stem.png"));
            TowerTop = ImageIO.read(new File("images/squaretower/top.png"));
            
            // Idle archer pictures
            idle_n = new ArrayList<Image>();
            int numberOfPictures = 37;
    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/idle1/idle1_fn_" + filenumber + ".png";
    			//System.out.println(filename);
    			idle_n.add(ImageIO.read(new File(filename)));
    		}
    		
    		idle_ne = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/idle1/idle1_fne_" + filenumber + ".png";
    			//System.out.println(filename);
    			idle_ne.add(ImageIO.read(new File(filename)));
    		}

    		idle_e = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/idle1/idle1_fe_" + filenumber + ".png";
    			//System.out.println(filename);
    			idle_e.add(ImageIO.read(new File(filename)));
    		}

    		idle_se = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/idle1/idle1_fse_" + filenumber + ".png";
    			//System.out.println(filename);
    			idle_se.add(ImageIO.read(new File(filename)));
    		}

    		idle_s = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/idle1/idle1_fs_" + filenumber + ".png";
    			//System.out.println(filename);
    			idle_s.add(ImageIO.read(new File(filename)));
    		}

    		idle_sw = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/idle1/idle1_fsw_" + filenumber + ".png";
    			//System.out.println(filename);
    			idle_sw.add(ImageIO.read(new File(filename)));
    		}

    		idle_w = new ArrayList<Image>();
    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/idle1/idle1_fw_" + filenumber + ".png";
    			//System.out.println(filename);
    			idle_w.add(ImageIO.read(new File(filename)));
    		}

    		idle_nw = new ArrayList<Image>();
            for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/idle1/idle1_fnw_" + filenumber + ".png";
    			//System.out.println(filename);
    			idle_nw.add(ImageIO.read(new File(filename)));
    		}
    		
            //ren!
            
            run_n = new ArrayList<Image>();
            numberOfPictures = 12;
    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/run/run_fn_" + filenumber + ".png";
    			//System.out.println(filename);
    			run_n.add(ImageIO.read(new File(filename)));
    		}
    		
    		run_ne = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/run/run_fne_" + filenumber + ".png";
    			//System.out.println(filename);
    			run_ne.add(ImageIO.read(new File(filename)));
    		}

    		run_e = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/run/run_fe_" + filenumber + ".png";
    			//System.out.println(filename);
    			run_e.add(ImageIO.read(new File(filename)));
    		}

    		run_se = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/run/run_fse_" + filenumber + ".png";
    			//System.out.println(filename);
    			run_se.add(ImageIO.read(new File(filename)));
    		}

    		run_s = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/run/run_fs_" + filenumber + ".png";
    			//System.out.println(filename);
    			run_s.add(ImageIO.read(new File(filename)));
    		}

    		run_sw = new ArrayList<Image>();

    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/run/run_fsw_" + filenumber + ".png";
    			//System.out.println(filename);
    			run_sw.add(ImageIO.read(new File(filename)));
    		}

    		run_w = new ArrayList<Image>();
    		for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/run/run_fw_" + filenumber + ".png";
    			//System.out.println(filename);
    			run_w.add(ImageIO.read(new File(filename)));
    		}

    		run_nw = new ArrayList<Image>();
            for (int filenumber = 1; filenumber < numberOfPictures; filenumber++)
    		{
    			String filename = "images/Archer/run/run_fnw_" + filenumber + ".png";
    			//System.out.println(filename);
    			run_nw.add(ImageIO.read(new File(filename)));
    		}
            
            
        } 
        catch (IOException e) 
        {
        	System.out.println(e.toString());
        }
	}
	public void setControls()
	{
		PanelNewObject = new JPanel();
		PanelNewObject.setLocation(550,370);
		PanelNewObject.setSize(240,105);
		PanelNewObject.setLayout(null);
		PanelNewObject.setOpaque(false);
		PanelNewObject.setBorder(new TitledBorder("New objects"));
		
		// PanelNewObject controls
		LblX = new JLabel();
		LblX.setLocation(5,18);
		LblX.setText("X: ");
		LblX.setSize(20,10);
		
		LblY = new JLabel();
		LblY.setLocation(5,33);
		LblY.setText("Y: ");
		LblY.setSize(20,10);
		
		LblHeight = new JLabel();
		LblHeight.setLocation(5,48);
		LblHeight.setText("H:");
		LblHeight.setSize(20,10);
		
		LblError = new JLabel();
		LblError.setLocation(200,90);
		LblError.setSize(50,15);
		LblError.setForeground(Color.RED);
		
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
		SpinHeight.setValue(1);
		
		String[] listItems = {"Archer", "Tower"};
		listObjects = new JList(listItems);
		listObjects.setLocation(110,10);
		listObjects.setSize(120, 76);
		listObjects.setBorder(BorderFactory.createLineBorder(Color.black));
		
		BtnAddFigure = new JButton();
		BtnAddFigure.setLocation(5, 65);
		BtnAddFigure.setSize(100,20);
		BtnAddFigure.setText("Add object");
		
		BtnAddFigure.addActionListener(new ActionListener() {
			// This event adds the archer to the map and calculates it's position
			public void actionPerformed(ActionEvent event) 
			{
				if (currentMap != null)
				{
					int Archer = 0;
					int Tower = 1;
					
					// Check if valid coordinates are being entered
	            	int x = Integer.parseInt(SpinX.getValue().toString());
	            	int y = Integer.parseInt(SpinY.getValue().toString());
	            	int height = Integer.parseInt(SpinHeight.getValue().toString());
					if (listObjects.getSelectedIndex() == Archer)
					{
						// Create an archer
						if(x > 0 && y > 0)
						{
						    int screenX;
						    int screenY;
						    int tempX;
						    int tempY;
						    
						    /* The input x/y from the spinners are X,Y coordinates same for the
						     * tiles. So this function calculates the screen position for the 
						     * units
						     */
						    
						    // First calculates how much the y input affects the final x coord
						    tempX = (startX + (tilewidth / 2)) - (y * (tilewidth / 2));
						    // Then calculates how much the x input affects the x coord
						    screenX = tempX + (x * (tilewidth / 2));
						    
						    // The same goes for the y coord
						    tempY = (startY - (tileheight / 2)) + (y * (tileheight / 2));
						    screenY = tempY + (x * (tileheight / 2));
						    
							Unit archer = new Unit(screenX, screenY, "Archer",Unit.Idle);
							List<Unit> mapUnits = currentMap.getMapUnits();
							mapUnits.add(archer);
							currentMap.SetMapUnits(mapUnits);
						}
					}
					else if(listObjects.getSelectedIndex() == Tower)
					{
						// Create a tower
						if(x > 0 && y > 0 && (height > 0 && height < 11))
						{
						    int screenX;
						    int screenY;
						    int tempX;
						    int tempY;
						    
						    /* The input x/y from the spinners are X,Y coordinates same for the
						     * tiles. So this function calculates the screen position for the 
						     * units
						     */
						    
						    // First calculates how much the y input affects the final x coord
						    tempX = (startX) - (y * (tilewidth / 2));
						    // Then calculates how much the x input affects the x coord
						    screenX = tempX + (x * (tilewidth / 2));
						    
						    // The same goes for the y coord
						    tempY = (startY - tileheight) + (y * (tileheight / 2));
						    screenY = tempY + (x * (tileheight / 2));
						    
						    // Create the bottom, set this map object stuck to a tile
						    MapObject towerBase = new MapObject(screenX, screenY,"TowerBase");
						    if (currentMap.getMapInfo().get(y).get(x) != null)
						    {
							    currentMap.getMapInfo().get(y).get(x).setMapobject(towerBase); 						    List<MapObject> mapObjects = currentMap.getMapObjects();
							    mapObjects.add(towerBase);
							    
							    // Add middlepieces
							    for (int i = 1; i < height; i++)
							    {
							    	screenY -= 48;
							    	MapObject middlePiece = new MapObject(screenX, screenY, "TowerStem");
							    	mapObjects.add(middlePiece);
							    }
							    // Add the top
							    screenY -= 96;
							    MapObject top = new MapObject(screenX,screenY,"TowerTop");
							    mapObjects.add(top);
							    // Add the new objects to the map
							    currentMap.setMapObjects(mapObjects);
						    }
						    else
						    {
						    	LblError.setText("There's already an object on this spot");
						    }
						}
					}
				}
			}
		});
		
		// Add the buttons to the PanelControls and the panelcontrols to the main class
		super.add(PanelNewObject);
		
		PanelNewObject.add(BtnAddFigure);
		PanelNewObject.add(LblX);
		PanelNewObject.add(LblY);
		PanelNewObject.add(LblHeight);
		PanelNewObject.add(SpinX);
		PanelNewObject.add(SpinY);
		PanelNewObject.add(SpinHeight);
		PanelNewObject.add(listObjects);
		PanelNewObject.add(LblError);
	}
	public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {
       
    }
    public void mouseExited(MouseEvent e) {

    }
    public void mouseClicked(MouseEvent e) {
    	MouseClick(e.getY(), e.getX(), e.getButton());
    }
}