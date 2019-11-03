package hotline_zombie;

import java.awt.Graphics;
import java.util.ArrayList;

//This class handles all the objects in the map (adds and deletes objects)
public class GameObjectHandler 
{

	ArrayList<Object> objectList = new ArrayList<Object>();	
	
	public void addObject(Object o) //Adds an object to the handler
	{
		objectList.add(o);
	}
	
	public void removeObject(Object o) //Removes an object from the handler
	{
		objectList.remove(o);
	}
	
	public void tick()				//Allows the handler to move each object
	{
		for(int i = 0; i < objectList.size(); ++i)
		{
			objectList.get(i).tick();
		}
	}
	
	public void render(Graphics g)   //Allows the handler to graphically render each object onto the screen
	{
		for (int i = 0; i < objectList.size(); ++i)
		{
			objectList.get(i).render(g);
		}
	}
}
