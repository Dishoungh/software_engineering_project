package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//This class describes the blocks that will be in the level to block the player from clipping into walls
public class Block extends Object
{
	public Block(int x, int y, Object_Type type)
	{
		super(x, y, type);
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillRect(x, y, 32, 32);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, 32, 32);
	}
}
