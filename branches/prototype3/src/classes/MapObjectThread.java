package classes;

import java.awt.Image;
import java.util.List;

import launcher.MainPanel;



public class MapObjectThread extends Thread {
		//private static Image idle;
	private MainPanel Main;
	public MapObjectThread(MainPanel Main)
	{
		this.Main = Main;
	}
	public void run() 
	{	
		while(true)
		{
			Map currentMap = Main.getCurrentMap();
			if (currentMap != null) // Check if there is a map to load
		    {
				// Update Unit sprites
				for (int unitcounter = 0; unitcounter < currentMap.getMapUnits().size(); unitcounter++)
				{
					Unit myUnit = currentMap.getMapUnits().get(unitcounter);
					int current = myUnit.getCurrentSpite();
					String Status = myUnit.getStatus();
					String Direction = myUnit.getDirection();
					
					int LastSprite = 1;
					if (Status == Unit.Idle)
					{
						LastSprite = Main.idleSprites;
					}
					else if (Status == Unit.Run)
					{
						LastSprite = Main.runSprites;
					}
					
					
					if (current == LastSprite)
					{
						myUnit.setCurrentSprite(1);	
					}
					else
					{
						myUnit.setCurrentSprite(current + 1);
					}			
				}
							
		    }
			try
			{
				super.sleep(50);
			}
			catch(InterruptedException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}
