	package gameCore;

	public class Unit extends MapObject {
		// static variables for the only current unit, an archer
		public final static int archerLength = 32;
		public final static int archerHeight = 32;
		public final static int archerSprites = 37;
		
		// variables
		public int direction = 0;
		private final int AbnormalityX = 32; 
		private final int AbnormalityY = -1;
		public int health = 100;
		public int currentsprite = 0;
		
		public Unit(int x, int y) {
			super("Unit");
			
			// Calculate the screen for this mapobject with it's own abnormality that
			// makes sure it paints in the middle of the tile.
			rawX = calcScreenX(x, y, AbnormalityX);
			rawY = calcScreenY(x, y, AbnormalityY);
		}

		public void moveTo(int x, int y) {

		}
	}
