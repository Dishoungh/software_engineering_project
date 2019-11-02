package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle; 

//This class describes the player actions (movement, etc.)
public class Player extends Object
{
	Object_Handler oHandler; //The handler will tell us the key inputs that were given to it and the player object will move in accordance to those inputs
	public Player(int x, int y, Object_Type type, Object_Handler oHandler)
	{
		super(x, y, type);
		xVelocity = 0; //He does not move for now (We will add key events/event handlers later)
		yVelocity = 0;
		this.oHandler = oHandler;
	}
	
	public void tick() //Player position will be updated here
	{
		x += xVelocity;
		y += yVelocity;
		
		hasCollided();
		
		//All key combinations here
		
		//Only Upward movement
		if(oHandler.isUp() && !oHandler.isLeft() && !oHandler.isRight() && !oHandler.isDown())
		{
			yVelocity = -5;
			xVelocity = 0;
		}
	
		//Only Downward movement
		if(!oHandler.isUp() && !oHandler.isLeft() && !oHandler.isRight() && oHandler.isDown())
		{
			yVelocity = 5;
			xVelocity = 0;
		}
		
		//Only Leftward movement
		if(!oHandler.isUp() && oHandler.isLeft() && !oHandler.isRight() && !oHandler.isDown())
		{
			yVelocity = 0;
			xVelocity = -5;
		}		
		
		//Only Rightward movement
		if(!oHandler.isUp() && !oHandler.isLeft() && oHandler.isRight() && !oHandler.isDown())
		{
			yVelocity = 0;
			xVelocity = 5;
		}
		
		//No Keys
		if(!oHandler.isUp() && !oHandler.isLeft() && !oHandler.isRight() && !oHandler.isDown())
		{
			yVelocity = 0;
			xVelocity = 0;
		}
		
		//Upward Left Diagonal Movement
		if(oHandler.isUp() && oHandler.isLeft() && !oHandler.isRight() && !oHandler.isDown())
		{
			yVelocity = -5;
			xVelocity = -5;
		}
		
		//Upward Right Diagonal Movement
		if(oHandler.isUp() && !oHandler.isLeft() && oHandler.isRight() && !oHandler.isDown())
		{
			yVelocity = -5;
			xVelocity = 5;
		}
		
		//Downward Left Diagonal Movement
		if(!oHandler.isUp() && oHandler.isLeft() && !oHandler.isRight() && oHandler.isDown())
		{
			yVelocity = 5;
			xVelocity = -5;
		}
		
		//Downward Right Diagonal Movement
		if(!oHandler.isUp() && !oHandler.isLeft() && oHandler.isRight() && oHandler.isDown())
		{
			yVelocity = 5;
			xVelocity = 5;
		}
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK); //Player will be a black rectangle for now
		g.fillRect(x, y, 24, 36);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, 24, 36);
	}
	
	private void hasCollided() //Keeps the player from clipping through walls
	{
		for(int i = 0; i < oHandler.objectList.size(); i++)
		{
			Object temp = oHandler.objectList.get(i);
			
			if(temp.getType() == Object_Type.Block && (this.getBounds().intersects(temp.getBounds())))
			{
				x += xVelocity * -1;
				y += yVelocity * -1;
			}
			
			if(temp.getType() == Object_Type.Zombie && (this.getBounds().intersects(temp.getBounds())))
			{
				x += xVelocity * -1;
				y += yVelocity * -1;
			}
		}
	}
	
}
