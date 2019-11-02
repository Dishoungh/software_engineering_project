package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//This class will describe zombie behavior (movement, etc.)
public class Zombie extends Object
{
	public Zombie(int x, int y, Object_Type type)
	{
		super(x, y, type);
	}
	
	public void tick() //Moves the object over each frame
	{
		x += xVelocity;
		y += yVelocity;
	}
	
	public void render(Graphics g) //Renders the object again each frame
	{
		g.setColor(Color.GREEN);
		g.fillRect(x, y, 25, 25);
	}
	
	public Rectangle getBounds() //We don't need this now but we will for object collision I think
	{
		return new Rectangle(x, y, 25, 25);
	}
}
