package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle; 

//This class describes the player actions (movement, etc.)
public class Player extends Object
{
	int moveIncr;
	Object_Handler oHandler; //The handler will tell us the key inputs that were given to it and the player object will move in accordance to those inputs
	public Player(int x, int y, int moveIncr, Object_Type type, Object_Handler oHandler)
	{
		super(x, y, type);
		xVelocity = 0; //He does not move for now (We will add key events/event handlers later)
		yVelocity = 0;
		this.moveIncr = moveIncr; 
		this.oHandler = oHandler;
	}
	
	public void tick() //Player position will be updated here
	{
		x += xVelocity;
		y += yVelocity;
		
		//All key combinations here
		
		xVelocity = ((oHandler.isRight() ? 1 : 0) - (oHandler.isLeft() ? 1 : 0)) * moveIncr;
		yVelocity = ((oHandler.isDown() ? 1 : 0) - (oHandler.isUp() ? 1 : 0)) * moveIncr;
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK); //Player will be a black rectangle for now
		g.fillRect(x, y, 32, 48);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, 32, 48);
	}
}
