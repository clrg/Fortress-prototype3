package gameCore;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameData {
	// Variables defined
	public final static int startX = 350;
	public final static int startY = 10;
	public final static int maxMapHeight = 10;
	public final static int archerLength = 32;
	public final static int archerHeight = 32;
	public final static int archerSprites = 37;
	public final static int dirE = 0;
	public final static int dirW = 1;
	public final static int dirS = 2;
	public final static int dirN = 3;
	
	// Code to load an image
	public static Image image;
	public static Image getImage(String ImageFile)
	{
		try
		{
			image = ImageIO.read(new File(ImageFile));
		}
		catch (IOException e) 
        {
        	System.out.println(e.toString());
        }
		return image;
	}
}
