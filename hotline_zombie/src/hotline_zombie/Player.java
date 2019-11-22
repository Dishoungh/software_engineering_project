package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle; 

//This class describes the player actions (movement, etc.)
public class Player extends Object
{
	private boolean up, down, left, right;
	int moveIncr;
	int width, height;
	GameObjectHandler oHandler; //The handler will tell us the key inputs that were given to it and the player object will move in accordance to those inputs
	public Player(int x, int y, int moveIncr, Object_Type type, GameObjectHandler oHandler)
	{
		super(x, y, type);
		xVelocity = 0; //He does not move for now (We will add key events/event handlers later)
		yVelocity = 0;
		this.moveIncr = moveIncr; 
		this.oHandler = oHandler;
		width = 24;
		height = 36;
	}
	
	public void tick() //Player position will be updated here
	{
		x += xVelocity;
		y += yVelocity;
		
		hasCollided();
		
		//All key combinations here
		
		xVelocity = ((this.isRight() ? 1 : 0) - (this.isLeft() ? 1 : 0)) * moveIncr;
		yVelocity = ((this.isDown() ? 1 : 0) - (this.isUp() ? 1 : 0)) * moveIncr;
		
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

	
	
}
