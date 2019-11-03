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
	
	//Constructor
	public MouseInput(GameObjectHandler oHandler, Camera camera, Player player)
	{
		this.oHandler = oHandler;
		this.camera = camera;
		this.player = player;
	}
	
	//Outputs a new bullet instance whenever mouse button is pressed
	public void mousePressed(MouseEvent e)
	{
		float vX;
		float vY;
			
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
				
		oHandler.addObject(new Bullet(player.getX(), player.getY(), Object_Type.Bullet, oHandler, vX, vY));
			
		}		
}

