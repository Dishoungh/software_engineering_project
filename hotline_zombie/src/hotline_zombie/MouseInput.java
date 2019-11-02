package hotline_zombie;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.IndexOutOfBoundsException;


//Describes what happens when we click on the mouse (should output a bullet object)
public class MouseInput extends MouseAdapter
{
	private GameObjectHandler oHandler;
	private Camera camera;
	
	public MouseInput(GameObjectHandler oHandler, Camera camera)
	{
		this.oHandler = oHandler;
		this.camera = camera;
	}
	
	public void mousePressed(MouseEvent e)
	{
		for(int i = 0; i < oHandler.objectList.size(); i++)
		{
			Object temp = oHandler.objectList.get(i);
			if(temp.getType() == Object_Type.Player)
			{
				//Inconsistent Speed (Sometimes the bullets will move so fast that it will blitz right past the Zombie objects)
				float vX;
				float vY;
				
				try
				{
					vX = (((e.getX() + camera.getX()) - temp.getX()) / 10);
					vY = (((e.getY() + camera.getY()) - temp.getY()) / 10);
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

				
				oHandler.addObject(new Bullet(temp.getX(), temp.getY(), Object_Type.Bullet, oHandler, vX, vY));
				
			}
		}
	}
}
