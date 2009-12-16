	package gameCore;

	public class Building extends MapObject 
	{
		private int height;
		public final static int towerheight = 48;
		public Building(int x, int y, int height) {
			super("Building"); // Do the constructor of the superclass
			this.height = height;
			
			// Calculate the x/y for this mapobject
			rawX = calcScreenX(x, y, 0);
			rawY = calcScreenY(x, y, 0);
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
	}
