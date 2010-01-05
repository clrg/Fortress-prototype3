package gameCore;

public class MapObject 
{
	public int rawX;
	public int rawY;
	public String type;
	public MapObject(String type)
	{
		this.type = type;
	}
	public boolean selected;
	public int calcScreenX(int x, int y, int abnormality)
	{
		// First calculates how much the y input affects the final x coord
	    int tempX = GameData.startX - (y * (Tile.tilewidth / 2));
	    // Then calculates how much the x input affects the x coord
	    int screenX = (tempX + (x * (Tile.tilewidth / 2))) + abnormality;
		
	    // return it with the abnormality (to get the object on the tile properly)
		return screenX;
	}
	public static int calcScreenY(int x, int y, int abnormality)
	{
		// The same goes for the y coord
	    int tempY = GameData.startY + (y * (Tile.tileheight / 2));
	    int screenY = (tempY + (x * (Tile.tileheight / 2))) + abnormality;
	    // return it with the abnormality (to get the object on the tile properly)
		return screenY;
	}
}
