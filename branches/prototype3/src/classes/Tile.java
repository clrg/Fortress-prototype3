package classes;

public class Tile {
	private MapObject mapobject;
	private String terrain;
	private Unit unit;
	
	public Tile()
	{
		this.terrain = "Grass";
	}
	public Tile(String terrain)
	{
		this.terrain = terrain;
	}
	public Tile(String terrain, MapObject mapobject)
	{
		this.terrain = terrain;
		this.mapobject = mapobject;
	}
	public String getTerrain()
	{
		return terrain;
	}
	public void setMapobject(MapObject mapobject) {
		this.mapobject = mapobject;
	}
	public MapObject getMapobject() {
		return mapobject;
	}
	public void setUnit(Unit  unit) {
		this. unit =  unit;
	}
	public Unit getUnit() {
		return  unit;
	}	
}
