package hotline_zombie;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.IndexOutOfBoundsException;
import java.math.*;



//Describes what happens when we click on the mouse (should output a bullet object)
@SuppressWarnings("unused")
public class MouseInput extends MouseAdapter
{
	private GameObjectHandler oHandler;
	private Camera camera;
	private Player player;
	
	private ImageLoader rot = new ImageLoader();
	
	//Constructor
	public MouseInput(GameObjectHandler oHandler, Camera camera, Player player)
	{
		this.oHandler = oHandler;
		this.camera = camera;
		this.player = player;
	}
	
	//Updates every time mouse location is updated
	public synchronized void mouseMoved(MouseEvent e)
      {
		//Get distances
		float xDist = e.getX() - player.getX() + camera.getX();
		float yDist = e.getY() - player.getY() + camera.getY();
		
		//Get angle to change
		double ratio = yDist/xDist;
		double rads = Math.atan(ratio);
		double angle = rads * (180/Math.PI) + 90;
		
		//Apply fix for Atan
		if (xDist < 0){
			angle+=180;
		}
		
		//Rotate the player to face mouse
		player.rotate((int)angle);
      }
	
	//Outputs a new bullet instance whenever mouse button is pressed
	public synchronized void mousePressed(MouseEvent e)
	{
		float vX;
		float vY;
		int posX;
		int posY;
		
		//Calculate x position
		if(e.getX() > player.getX() + 44) //Right bound
		{
			posX = player.getX() + 44;
		}
		else if(e.getX() < player.getX()) //Left bound
		{
			posX = player.getX();
		}
		else 
		{
			posX = e.getX();
		}
		
		//Calculate y position

		if(e.getY() + 100 > player.getY() + 44) //Right bound
		{
			posY = player.getY() + 44;
		}
		else if(e.getY() < player.getY()) //Left bound
		{
			posY = player.getY();
		}
		else 
		{
			posY = e.getY() + 20;
		}
			
		try
		{
			float distanceX = e.getX() - player.getX() + camera.getX();
			float distanceY = e.getY() - player.getY() + camera.getY();
			
			int constantSpeed = 30;
			
			float ratio = distanceX / distanceY;
			
			if(Math.abs(distanceX) >= Math.abs(distanceY))
			{
				vX = constantSpeed * (distanceX / Math.abs(distanceX));
				vY = distanceY * (vX / distanceX);
			}
			else
			{
				vY = constantSpeed * (distanceY / Math.abs(distanceY));
				vX = distanceX * (vY / distanceY);
			}
		}
		catch(IndexOutOfBoundsException err1)
		{
			vX = 5;
			vY = 0;
			System.out.println("The location you tried to shoot at is Out of Bounds!");
		}
		catch(NullPointerException err2)
		{
			vX = 5;
			vY = 0;
			System.out.println("There has been a null exception!");
		}
				
		oHandler.addObject(new Bullet(posX, posY, Object_Type.Bullet, oHandler, vX, vY));
		
		oHandler.printArraySize();
	}		
}

