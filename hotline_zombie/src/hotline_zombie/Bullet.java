package hotline_zombie;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Bullet extends Object
{
	private Object_Handler oHandler;
	
	public Bullet(int x, int y, Object_Type type, Object_Handler oHandler, float vX, float vY)
	{
		super(x, y, type);
		this.oHandler = oHandler;
		
		xVelocity = vX;
		yVelocity = vY;
		
	}
	
	public void tick()
	{
		x += xVelocity;
		y += yVelocity;
		
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
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 5, 5);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, 5, 5);
	}
}
