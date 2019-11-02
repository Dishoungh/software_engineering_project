package hotline_zombie;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


//Describes what happens when we click on the mouse (should output a bullet object)
public class MouseInput extends MouseAdapter
{
	private Object_Handler oHandler;
	private Camera camera;
	
	public MouseInput(Object_Handler oHandler, Camera camera)
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
				
				//Inconsistent speed (Fix it)
				float vX = (((e.getX() + camera.getX()) - temp.getX()) / 10);
				float vY = (((e.getY() + camera.getY()) - temp.getY()) / 10);
				
				oHandler.addObject(new Bullet(temp.getX(), temp.getY(), Object_Type.Bullet, oHandler, vX, vY));
				
			}
		}
	}
}
