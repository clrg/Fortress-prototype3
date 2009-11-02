package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Map
{
	private List<List<Tile>> mapInfo;
	private List<Unit> mapUnits;
	private List<MapObject> mapObjects;
	private int maxX;
	private int maxY;
	private String mapName;
	private Tile myTile;
	
	
	// Constructor
	public Map(String mapName,int maxX, int maxY, String Terrain)
	{
		this.maxX = maxX;
		this.maxY = maxY;
		this.mapName = mapName;
		this.mapUnits = new ArrayList<Unit>();
		mapObjects = new ArrayList<MapObject>();
		// Create the lists from the parameters
		mapInfo = new ArrayList<List<Tile>>();
        for(int y = 0; y < maxY; y++)
        {
        	List<Tile> mapXlist = new ArrayList<Tile>();
        	for(int x = 0; x < maxX; x++)
        	{
        		myTile = new Tile(Terrain);
        		mapXlist.add(x,myTile);
        	}
        	mapInfo.add(mapXlist);
        }
	}
	/* Constructor 2 This constructor is for if another function already created the
	 * mapinfo list. */
	public Map(String mapName,List<List<Tile>> mapInfo, Tile myTile)
	{
		this.mapName = mapName;
		this.myTile = myTile;
		this.mapInfo = mapInfo;
		/* There's gotta come a function here that gets the maxY and maxX from the 
		 * mapinfo and puts it in the variables */
	}
	// Getter
	public List<List<Tile>> getMapInfo() {
		return mapInfo;
	}
	public List<Unit> getMapUnits()
	{
		if(mapUnits.size() > 1){
		
			//Order the list so the repainting goes in the right order
			List <Unit> myOrderdList = new ArrayList<Unit>();
			List <Unit> myTmpList = new ArrayList<Unit>();
			myOrderdList.add(mapUnits.get(0));
			int Index = 0;
			for(int x = 1; x < mapUnits.size(); x++){
				for(int y = 0; y < myOrderdList.size(); y++){
					if(mapUnits.get(x).getY() < myOrderdList.get(y).getY())
					{
						Index = y;
						//Exit for
						y = myOrderdList.size();
					}
					else
						Index = myOrderdList.size();
				}
				myOrderdList.add(Index, mapUnits.get(x));
			}

			return myOrderdList;
		}
		else
			return this.mapUnits;
	}
	public void SetMapUnits(List<Unit> mapUnits)
	{
		this.mapUnits = mapUnits;
	}
	public void setMapObjects(List<MapObject> mapObjects) {
		this.mapObjects = mapObjects;
	}
	public List<MapObject> getMapObjects() {
		return mapObjects;
	}
}

