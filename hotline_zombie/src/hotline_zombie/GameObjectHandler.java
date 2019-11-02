package hotline_zombie;

import java.awt.Graphics;
import java.util.ArrayList;

//This class handles all the objects in the map (adds and deletes objects)
public class GameObjectHandler 
{
	private boolean up, down, left, right;
	ArrayList<Object> objectList = new ArrayList<Object>();	
	
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	
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
