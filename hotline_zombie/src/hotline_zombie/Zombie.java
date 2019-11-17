package hotline_zombie;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;

//This class will describe zombie behavior (movement, etc.)
@SuppressWarnings("unused")
public class Zombie extends Object
{
	GameObjectHandler oHandler;
	ImageLoader loader = new ImageLoader(); //ImageLoader for zombie Image
	BufferedImage zombieImage = loader.loadImage("/zombie.png"); //Load Zombie Image
	int rot = 5;
	
	
	private int health;
	
	public Zombie(int x, int y, Object_Type type, GameObjectHandler oHandler)
	{
		super(x, y, type);
		health = 2;
		this.oHandler = oHandler;
	}
	
	public synchronized void tick() //Moves the object over each frame
	{
		x += xVelocity;
		y += yVelocity;
	}
	
	public synchronized void render(Graphics g) //Renders the object again each frame
	{
		//g.drawImage(zombieImage, x, y, 25, 25, null);
		g.drawImage(loader.rotImage(rot, zombieImage), x, y, 44, 44, null); //Draw Zombie Image (Bug with black backgrounds)
		//g.setColor(Color.GREEN);
		//g.fillRect(x, y, 25, 25);
	}
		
	public synchronized void decrHealth() //Decrements zombie's health
	{
		if(health > 0)
		{
			health--;
		}
		
		checkHealth();
	}
	
	public synchronized void checkHealth()
	{
		if(health <= 0)
		{
			oHandler.removeObject(this);
		}
	}
	
	public Rectangle getBounds() 
	{
		return new Rectangle(x, y, 50, 50);
	}
}
