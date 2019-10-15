package hotline_zombie;
import java.awt.Graphics;
import java.util.ArrayList;

//This class handles all the objects in the map (adds and deletes objects)
public class Object_Handler 
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
			Object o = objectList.get(i);
			o.tick();
		}
	}
	
	public void render(Graphics g)   //Allows the handler to graphically render each object onto the screen
	{
		for (int i = 0; i < objectList.size(); ++i)
		{
			Object o = objectList.get(i);
			o.render(g);
		}
	}
}
