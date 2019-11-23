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
		
		hasCollided();
		
		for(int i = 0; i < oHandler.objectList.size(); i++)
		{
			Object temp = oHandler.objectList.get(i);
			
			if(temp.getType() == Object_Type.Player )
			{
	
				//Calculate Distance from Zombie:
				//to left of the player
				double L_Distance = Math.abs(this.x + 25 - temp.x);
				//to right the player
				double R_Distance = Math.abs(temp.x + 24 - this.x);
				//above the player
				double U_Distance = Math.abs(this.y - temp.y + 25);
				//below the player
				double D_Distance = Math.abs(this.y - temp.y); 
				
				 if(L_Distance <= 3)
				{
					xVelocity = 1;
					L_Distance++;
				}
				else if(R_Distance <= 3)
				{
					xVelocity = -1;
					R_Distance--;
				} 
			    if(U_Distance <= 3 )
				{
					yVelocity = 1;
					U_Distance++;
				}  
				else if(D_Distance <= 3)
				{
					yVelocity = -1;		
					D_Distance++;
				} 
				
			}
		}
	
	}
	private void hasCollided() //Keeps the zombie from clipping through walls and the player
	{
		for(int i = 0; i < oHandler.objectList.size(); i++)
		{
			Object temp = oHandler.objectList.get(i);
			
			if(temp.getType() == Object_Type.Block && (this.getBounds().intersects(temp.getBounds())))
			{
				x += xVelocity * -1;
				y += yVelocity * -1;
			}
			
			if(temp.getType() == Object_Type.Player && (this.getBounds().intersects(temp.getBounds())))
			{
				x += xVelocity * -1;
				y += yVelocity * -1;
			}
		}
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
			oHandler.decrZombies();
		}
	}
	
	public Rectangle getBounds() 
	{
		return new Rectangle(x, y, 50, 50);
	}
	
}