package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
//import MouseInput.java;

//This class describes the player actions (movement, etc.)
public class Player extends Object
{
	private boolean up, down, left, right;
	int moveIncr;
	GameObjectHandler oHandler; //The handler will tell us the key inputs that were given to it and the player object will move in accordance to those inputs
	
	ImageLoader loader = new ImageLoader(); //ImageLoader for Player Image
	BufferedImage playerImage = loader.loadImage("/player.png"); //Load Player Image	
	BufferedImage img = loader.loadImage("/player.png"); //Load Player Image for manipulation
	
	public Player(int x, int y, int moveIncr, Object_Type type, GameObjectHandler oHandler)
	{
		super(x, y, type);
		xVelocity = 0; //He does not move for now (We will add key events/event handlers later)
		yVelocity = 0;
		this.moveIncr = moveIncr; 
		this.oHandler = oHandler;
	}
	
	public synchronized void tick() //Player position will be updated here
	{
		x += xVelocity;
		y += yVelocity;
		
		hasCollided();
		
		//All key combinations here
		
		xVelocity = ((this.isRight() ? 1 : 0) - (this.isLeft() ? 1 : 0)) * moveIncr;
		yVelocity = ((this.isDown() ? 1 : 0) - (this.isUp() ? 1 : 0)) * moveIncr;
		
	}
	
	//Function to rotate player based off of original image towards mouse
	public void rotate(int angle){
		//Rotate player based on mouse input
		img = loader.rotImage(angle, playerImage);
	}
	
	public synchronized void render(Graphics g)
	{
		g.drawImage(img, x, y, 44, 44, null); //draw player
		//g.setColor(Color.BLACK); //Player will be a black rectangle for now
		//g.fillRect(x, y, 24, 36);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, 38, 38);
	}
	
	private synchronized void hasCollided() //Keeps the player from clipping through walls
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
