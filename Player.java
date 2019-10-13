package hotline_zombie;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle; 

//This class describes the player actions (movement, etc.)
public class Player extends Object
{
	public Player(int x, int y)
	{
		super(x, y);
		xVelocity = 0; //He does not move for now (We will add key events/event handlers later)
		yVelocity = 0;
	}
	
	public void tick()
	{
		x += xVelocity;
		y += yVelocity;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK); //Player will be a black rectangle for now
		g.fillRect(x, y, 25, 25);
	}
	
	public Rectangle getBounds()
	{
		return null;
	}
}
