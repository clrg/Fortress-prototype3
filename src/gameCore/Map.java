package gameCore;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Map 
{
	// Objects
	private List<List<Tile>> mapTiles;
	public MapObject selectedMapObject;
	
	// Variables
	public boolean gridview = true;
	public boolean coordinates = true;
	public String mapName;
	private String terrain;
	public int mapX;
	public int mapY;
	
	// Images
	private Image grassImage;
	private Image tileImage;
	private List<Image> spritesArcherIdleEast;
	private List<Image> spritesArcherIdleSouth;
	private List<Image> spritesArcherIdleWest;
	private List<Image> spritesArcherIdleNorth;
	private Image towerbaseSprite;
	private Image towerstemSprite;
	private Image towertopSprite;
	private Image hpbar100;
	
	public Map(String mapName, int mapX, int mapY, String terrain) 
	{
		// Setting the variables
		this.mapName = mapName;
		this.setTerrain(terrain);
		this.mapX = mapX;
		this.mapY = mapY;
		
		// Load the sprites
		getSprites();
		
		// Making sure
		selectedMapObject = new MapObject("none");
		
		mapTiles = new ArrayList<List<Tile>>();
        for(int y = 0; y < mapY; y++)
        {
        	List<Tile> mapXlist = new ArrayList<Tile>();
        	for(int x = 0; x < mapX; x++)
        	{
        		Tile myTile = new Tile();
        		mapXlist.add(x,myTile);
        	}
        	mapTiles.add(mapXlist);
        }
	}
	
	public void deselectMapObject()
	{
		// Set a non existing mapobject as selectedmapobject
		selectedMapObject.setSelected(false);
		selectedMapObject = new MapObject("none");
	}
	public void selectMapObject(int mouseX, int mouseY)
	{
		// Select an object based on the mouse input
		// First the basic loops all tiles and checks all the mapobjects of all tiles
		for (int y = 0; y < getMapTiles().size(); y++)
	    {
			for (int x = 0; x < getMapTiles().get(y).size(); x++)
			{
				if(getMapTiles().get(y).get(x).mapobject.type == "Unit")
				{
					// Check on if the click is on the archers sprite 
					if((mouseX > getMapTiles().get(y).get(x).mapobject.rawX &&
						mouseX < getMapTiles().get(y).get(x).mapobject.rawX + GameData.archerLength) &&
						(mouseY > (getMapTiles().get(y).get(x).mapobject.rawY) &&
						mouseY < getMapTiles().get(y).get(x).mapobject.rawY + GameData.archerHeight) )
					{
						getMapTiles().get(y).get(x).mapobject.setSelected(true);
						selectedMapObject = getMapTiles().get(y).get(x).mapobject;
					}
				}
				if(getMapTiles().get(y).get(x).mapobject.type == "Building")
				{
					// Here the code on how to select a tower
				}
			}	
		}
	}
	public void repaint(Graphics g)
	{
		int tempX = GameData.startX;
		int tempY = GameData.startY;
		
		// The ownage map builder
    	for (int y = 0; y < getMapTiles().size(); y++)
	    {
			for (int x = 0; x < getMapTiles().get(y).size(); x++)
			{
				if(getTerrain() == "Grass")
				{
					// draw the grass
					g.drawImage(grassImage,tempX,tempY, null);
					if (gridview == true)
					{
						g.drawImage(tileImage,tempX,tempY, null);
					}
					// draw the coordinates if it's turned on
					if (coordinates == true)
					{
						String coord = (x) + "," + (y);
						g.setColor(Color.WHITE);
						
						// the -8 and +3 are to position the coordinate in the middle of the tile
						g.drawString(coord, (tempX + (Tile.tilewidth /2)-8 ),(tempY+ (Tile.tileheight /2)+3));
						g.drawString("Y", 100, 100);
						g.drawString("X", 700, 100);
				}
				
				// Paint the mapobject	
				// Set the mapobject in a temp object so it's shorter to type.
				MapObject currentMapObject = getMapTiles().get(y).get(x).mapobject;
				if (currentMapObject.type == "Unit")
				{
					int archerX = currentMapObject.rawX;
					int archerY = currentMapObject.rawY;
				    
					Unit archer = (Unit) currentMapObject;
					if(archer.currentsprite == 36)
					{
						archer.currentsprite = 0;
						g.drawImage(spritesArcherIdleEast.get(0), archerX, archerY,null);
						archer.currentsprite++;
					}
					else
					{
						g.drawImage(spritesArcherIdleEast.get(archer.currentsprite), archerX, archerY,null);
						archer.currentsprite++;
					}	
					if(currentMapObject == selectedMapObject)
					{
						g.drawImage(hpbar100, archerX, archerY,null);
					}
				}
				else if (getMapTiles().get(y).get(x).mapobject.type == "Building")
				{
						int towerX = currentMapObject.rawX;
						int towerY = currentMapObject.rawY;
						g.drawImage(towerbaseSprite, towerX, towerY, null);
						
						Building tower = (Building) currentMapObject;
						
						if(tower.getHeight() > 0)
						{
							for (int i = 0; i < tower.getHeight(); i++)
							{
								towerY -= Building.towerheight;
								g.drawImage(towerstemSprite,towerX, towerY,null);
							}
						}
						g.drawImage(towertopSprite,towerX, (towerY - (Building.towerheight*2)),null);
					}
				}			
				tempX += (Tile.tilewidth / 2);
				tempY += (Tile.tileheight / 2);
			}
			tempX = (GameData.startX - ((y+1) * (Tile.tilewidth / 2)));
			tempY = (GameData.startY + ((y+1) * (Tile.tileheight / 2)));
    	}
	}
	public void getSprites()
	{
		// Load all images at once (if you do this in the paintComponent,
		// it has to load it every time and that makes the application slow.
		grassImage = GameData.getImage("images/grass.gif");
		tileImage = GameData.getImage("images/grid.png");
		towerbaseSprite = GameData.getImage("images/squaretower/base.png");
		towerstemSprite = GameData.getImage("images/squaretower/stem.png");
		towertopSprite = GameData.getImage("images/squaretower/top.png");
		hpbar100 = GameData.getImage("images/full.png");
		
		spritesArcherIdleEast = new ArrayList<Image>();
		spritesArcherIdleNorth = new ArrayList<Image>();
		spritesArcherIdleWest = new ArrayList<Image>();
		spritesArcherIdleSouth = new ArrayList<Image>();
		// loop that loads all archer sprites facing to south,east,north and west
		for (int i = 1; i < GameData.archerSprites; i++)
		{
			spritesArcherIdleWest.add(GameData.getImage("images/Archer/idle1/idle1_fe_" + Integer.toString(i) + ".png"));
			spritesArcherIdleEast.add(GameData.getImage("images/Archer/idle1/idle1_fw_" + Integer.toString(i) + ".png"));
			spritesArcherIdleNorth.add(GameData.getImage("images/Archer/idle1/idle1_fn_" + Integer.toString(i) + ".png"));
			spritesArcherIdleSouth.add(GameData.getImage("images/Archer/idle1/idle1_fs_" + Integer.toString(i) + ".png"));
		}
	}
	public List<List<Tile>> getMapTiles()
	{
		return mapTiles;
	}
	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	public String getTerrain() {
		return terrain;
	}
}
