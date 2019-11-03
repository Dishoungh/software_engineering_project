package hotline_zombie;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.IndexOutOfBoundsException;


//Describes what happens when we click on the mouse (should output a bullet object)
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
		//Inconsistent Speed (Sometimes the bullets will move so fast that it will blitz right past the Zombie objects)
		float vX;
		float vY;
			
		try
		{
			vX = (((e.getX() + camera.getX()) - player.getX()) / 10);
			vY = (((e.getY() + camera.getY()) - player.getY()) / 10);
		}
		catch(IndexOutOfBoundsException b)
		{
			vX = 5;
			vY = 0;
			System.out.println("The location you tried to shoot at is Out of Bounds!");
		}
		catch(NullPointerException c)
		{
			vX = 5;
			vY = 0;
			System.out.println("There has been a null exception!");
		}
				
		oHandler.addObject(new Bullet(player.getX(), player.getY(), Object_Type.Bullet, oHandler, vX, vY));
			
		}		
}

