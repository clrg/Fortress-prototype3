package gameCore;

public class Tile 
{
	public MapObject mapobject;
	public final static int tilewidth = 97;
	public final static int tileheight = 49;
	public Tile()
	{
		mapobject = new MapObject("nothing");
	}
	
	/**
	 * Setter of the <tt>mapobject</tt>
	 * 
	 * @param mapobject
	 *            Sets the mapobject.
	 */
	public void setMapobject(MapObject mapobject)
	{
		this.mapobject = mapobject;
	}
}
