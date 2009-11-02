package classes;

public class Unit {
	public static String Run = "run";
	public static String Idle = "idle";
	
	public static String NorthWest = "nw";
	public static String North = "n";
	public static String NorthEast = "ne";
	public static String East = "e";
	public static String SouthEast = "se";
	public static String South = "s";
	public static String SouthWest = "sw";
	public static String West = "w";
	
	private String status;
	private int x;
	private int y;
	private String type;
	private int currentSprite;
	private String direction;
	private boolean selected;
	private int MoveToX;
	private int MoveToY;
	public Unit(int x, int y, String type,String status){
		this.status = status;
		this.x = x;
		this.y = y;
		this.type = type;
		this.currentSprite = 1;
		this.direction = NorthWest;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getStatus()
	{
		return status;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public int getCurrentSpite() {
		return currentSprite;
	}
	public void setCurrentSprite(int currentSprite) {
		this.currentSprite = currentSprite;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getType()
	{
		return type;
	}
	public void setDirection(String direction)
	{
		this.direction = direction;
	}
	public String getDirection()
	{
		return direction;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean getSelected() {
		return selected;
	}
	public void setMoveToX(int x) {
		this.MoveToX = x;
	}
	public int getMoveToX() {
		return MoveToX;
	}
	public void setMoveToY(int y) {
		this.MoveToY = y;
	}
	public int getMoveToY() {
		return MoveToY;
	}
}
