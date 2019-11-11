package hotline_zombie;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;

//This class will describe zombie behavior (movement, etc.)
public class Zombie extends Object
{
	ImageLoader loader = new ImageLoader(); //ImageLoader for zombie Image
	BufferedImage zombieImage = loader.loadImage("/zombie.png"); //Load Zombie Image
	int rot = 5;
	
	public Zombie(int x, int y, Object_Type type)
	{
		super(x, y, type);
	}
	
	public void tick() //Moves the object over each frame
	{
		x += xVelocity;
		y += yVelocity;
	}
	
	public void render(Graphics g) //Renders the object again each frame
	{
		//g.drawImage(zombieImage, x, y, 25, 25, null);
		g.drawImage(loader.rotImage(rot, zombieImage), x, y, 44, 44, null); //Draw Zombie Image (Bug with black backgrounds)
		//g.setColor(Color.GREEN);
		//g.fillRect(x, y, 25, 25);
	}
	
	public Rectangle getBounds() //We don't need this now but we will for object collision I think
	{
		return new Rectangle(x, y, 25, 25);
	}
}
