package gameCore;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Map 
{
	// Objects
	private List<List<Tile>> mapTiles;
	public MapObject selectedMapObject;
	// This is used for pathfinding to see what tiles can't be
	public Point selectedPosition = new Point((int)Double.MAX_VALUE,(int)Double.MAX_VALUE);
	public List<Point> OpenTilesList = new ArrayList<Point>();
	public List<Point> ClosedTilesList = new ArrayList<Point>();
	
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
	private Image bighpbar100;
	
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
        		OpenTilesList.add(new Point(x,y));
        	}
        	mapTiles.add(mapXlist);
        }
	}
	
	public void deselectMapObject()
	{
		// Set a non existing mapobject as selectedmapobject
		selectedMapObject.selected = false;
		selectedMapObject = new MapObject("none");
	}
	public void click(Point mouseClick, int mousekey)
	{
		int tempX = GameData.startX;
		int tempY = GameData.startY;
		
		// The closest Tile to where you clicked
		Point closestTile = new Point(0,0);
		// The distance to the closest tile
		double closestDistance = Double.MAX_VALUE; 
		
		// The ownage map builder to generate coordinates for every 
    	for (int y = 0; y < getMapTiles().size(); y++)
	    {
			for (int x = 0; x < getMapTiles().get(y).size(); x++)
			{
				int posX = tempX + (Tile.tilewidth / 2);
				int posY = tempY + (Tile.tileheight / 2);
				
				double diffX = (double) makePositive(posX-mouseClick.x);
				double diffY = (double) makePositive(posY-mouseClick.y);
				double tempDistance = (Math.sqrt((diffX * diffX)+(diffY * diffY)));
				if(tempDistance <= closestDistance)
				{
					closestDistance = tempDistance;
					closestTile = new Point(x,y);
				}
			
				tempX += (Tile.tilewidth / 2);
				tempY += (Tile.tileheight / 2);
			}
			tempX = (GameData.startX - ((y+1) * (Tile.tilewidth / 2)));
			tempY = (GameData.startY + ((y+1) * (Tile.tileheight / 2)));
    	}
    	// Selecting a mapobject
		int leftMouseKey = 1;
		int rightMouseKey = 2;
		
		if(mousekey == leftMouseKey)
		{
			// First deselect the current object.
			deselectMapObject();
			selectMapObject(closestTile);
			selectedPosition = closestTile;
		}
		else if(mousekey == rightMouseKey)
		{
			// Select a position to walk to
			selectPositionToMoveTo(closestTile);
		}
	}
	public void selectMapObject(Point selectedPos)
	{
		selectedMapObject = getMapTiles().get(selectedPos.y).get(selectedPos.x).mapobject;
		getMapTiles().get(selectedPos.y).get(selectedPos.x).mapobject.selected = true;
	}
	public void selectPositionToMoveTo(Point Endpoint)
	{
		// The start and end points in tile coordinates
		Point Start = selectedPosition;
		Point End = Endpoint;
		
		// The acual tiles
		Tile startTile = getMapTiles().get(Start.y).get(Start.x);
		Tile endTile = getMapTiles().get(End.y).get(End.x);
		
		// The screen coordinates of the tiles
		Point rawStart = new Point(startTile.mapobject.rawX,startTile.mapobject.rawY);
		Point rawEnd = new Point(endTile.mapobject.rawX,endTile.mapobject.rawY);
		
		double diffX = (double) makePositive(rawStart.x - rawEnd.x);
		double diffY = (double) makePositive(rawStart.y - rawEnd.y);
		double distance = (Math.sqrt((diffX * diffX)+(diffY * diffY)));
		
		
		
		/* first calculate the differences between the starting point and the end point
		int diffX = start.x-end.x;
		int diffY = start.y-end.y;
		
		// Calculate the amount of tiles the unit has to walk
		int distance = Route.makePositive(diffX) + Route.makePositive(diffY);
		*/
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
						if(getMapTiles().get(y).get(x).mapobject.selected == true)
						{
							g.drawImage(bighpbar100,towerX,(towerY-Building.towerheight),null);
						}
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
		bighpbar100 = GameData.getImage("images/bigfull.png");
		
		spritesArcherIdleEast = new ArrayList<Image>();
		spritesArcherIdleNorth = new ArrayList<Image>();
		spritesArcherIdleWest = new ArrayList<Image>();
		spritesArcherIdleSouth = new ArrayList<Image>();
		// loop that loads all archer sprites facing to south,east,north and west
		for (int i = 1; i < Unit.archerSprites; i++)
		{
			spritesArcherIdleWest.add(GameData.getImage("images/Archer/idle1/idle1_fe_" + Integer.toString(i) + ".png"));
			spritesArcherIdleEast.add(GameData.getImage("images/Archer/idle1/idle1_fw_" + Integer.toString(i) + ".png"));
			spritesArcherIdleNorth.add(GameData.getImage("images/Archer/idle1/idle1_fn_" + Integer.toString(i) + ".png"));
			spritesArcherIdleSouth.add(GameData.getImage("images/Archer/idle1/idle1_fs_" + Integer.toString(i) + ".png"));
		}
	}
	public static int makePositive(int input)
	{
		// make them possitive 
		if(input < 0)
		{
			return -input;
		}
		else
		{
			return input;
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
