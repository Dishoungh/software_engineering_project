package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
//import MouseInput.java;

//This class describes the player actions (movement, etc.)
@SuppressWarnings("unused")
public class Player extends Object
{
	private boolean up, down, left, right;
	private boolean alive, invincible, pauseGame;
	//Player's health here. When health is 0, player dies.
	private int health, maxHealth, playerWidth, playerHeight;         

	
	
	int moveIncr;
	GameObjectHandler oHandler; //The handler will tell us the key inputs that were given to it and the player object will move in accordance to those inputs
	
	ImageLoader loader;
	// Static player image and player image for manipulation (respectively).
	BufferedImage playerImage, img; 
	
	public Player(int x, int y, int moveIncr, Object_Type type, GameObjectHandler oHandler)
	{
		super(x, y, type);
		
		playerWidth = playerHeight = 44;
		maxHealth = 5;
		
		loader = new ImageLoader();
		playerImage = loader.loadImage("/player.png");
		img = playerImage;
		
		xVelocity = 0; //He does not move for now (We will add key events/event handlers later)
		yVelocity = 0;
		this.moveIncr = moveIncr; 
		this.oHandler = oHandler;
		
		this.health = 5;   //Player starts out with 5 health
		this.alive = true;
		this.invincible = false;
		this.pauseGame = false;
	}
	
	public synchronized void tick() //Player position will be updated here
	{
		x += xVelocity;
		y += yVelocity;
		
		hasCollided();
		checkHealth();
		
		//All key combinations here
		
		xVelocity = ((this.isRight() ? 1 : 0) - (this.isLeft() ? 1 : 0)) * moveIncr;
		yVelocity = ((this.isDown() ? 1 : 0) - (this.isUp() ? 1 : 0)) * moveIncr;
		
	}
	
	//Function to rotate player based off of original image towards mouse
	public synchronized void rotate(int angle){
		//Rotate player based on mouse input
		img = loader.rotImage(angle, playerImage);
	}
	
	public synchronized void render(Graphics g)
	{
		g.drawImage(img, x, y, playerHeight, playerWidth, null); //draw player
		
		int playerCenterX = x + playerWidth/2;
		int playerCenterY = y + playerHeight/2;
		int healthWidth= 20, healthHeight = 15;
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
			
			if(temp.getType() == Object_Type.Block && (this.getBounds().intersects(temp.getBounds()))) //If player intersects with a Block
			{
				x += xVelocity * -1;
				y += yVelocity * -1;
			}
			
			if(temp.getType() == Object_Type.Zombie && (this.getBounds().intersects(temp.getBounds()) || temp.getBounds().intersects(this.getBounds()))) //If player intersects with a Zombie
			{
				x += xVelocity * -1;
				y += yVelocity * -1;
				
				//Take health off
				if(!invincible)
				{
					if(health > 0)
					{
						decrHealth();
					}
					
					//Count down invinciblity for 2 seconds
					Timer timer = new Timer(this);
				}
			}
		}
	}
	
	private synchronized void checkHealth() //Checks player's health (When player's health is 0, player dies)
	{
		if(alive)
		{
			if(health <= 0) //Player dies
			{
				health = 0;
				alive = false;
			}
		}
	}
	
	//Setters and Getters of directional functions and attributes for the player
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
	
	public synchronized void decrHealth()
	{
		health--;
	}
	
	public synchronized boolean isInvincible()
	{
		return invincible;
	}
	
	public synchronized void setInvincibility(boolean invinc)
	{
		this.invincible = invinc;
	}
	
	public synchronized int getHealth()
	{
		return health;
	}
	
	public synchronized boolean isAlive()
	{
		return alive;
	}
	
	

	public synchronized boolean getPaused()
	{
		return pauseGame;
	}
	
	public synchronized void setPause(boolean pauseGame)
	{
		this.pauseGame = pauseGame;
	}
	
}
