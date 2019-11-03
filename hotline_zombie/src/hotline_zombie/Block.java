package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//This class describes the blocks that will be in the level to block the player from clipping into walls
public class Block extends Object
{
	
	private final int WIDTH = 32;      //This establishes the width size of each block
	private final int HEIGHT = 32;	   //This establishes the height size of each block
	
	//Constructor
	public Block(int x, int y, Object_Type type)
	{
		super(x, y, type);
	}
	
	//Blocks are still objects, so they shouldn't have any positional changes
	public void tick() 
	{
		
	}
	
	//Renders the block
	public void render(Graphics g) 
	{
		g.setColor(Color.RED);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
	
	//Creates a rectangular boundary of the block
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
}
