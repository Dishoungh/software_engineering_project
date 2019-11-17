package hotline_zombie;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Bullet extends Object
{
	private GameObjectHandler oHandler;
	
	private final int WIDTH = 5;  //Width of the bullet
	private final int HEIGHT = 5; //Height of the bullet
	
	//Constructor
	public Bullet(int x, int y, Object_Type type, GameObjectHandler oHandler, float vX, float vY)
	{
		super(x, y, type);
		this.oHandler = oHandler;
		
		xVelocity = vX;
		yVelocity = vY;
		
	}
	
	//Bullet position changes here
	public synchronized void tick()
	{
		x += xVelocity;
		y += yVelocity;
		
		//Checks if the bullet has collided with anything (Blocks or Zombies)
		for(int i = 0; i < oHandler.objectList.size(); i++)
		{
			Object temp = oHandler.objectList.get(i);
			
			if(temp.getType() == Object_Type.Block || temp.getType() == Object_Type.Zombie)
			{
				if(getBounds().intersects(temp.getBounds()))
				{
					oHandler.removeObject(this);
					break;
				}
			}
		}
		
	}
	
	//Renders the bullet into view 
	public synchronized void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
	
	//Sets boundaries for the bullet object
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
}
