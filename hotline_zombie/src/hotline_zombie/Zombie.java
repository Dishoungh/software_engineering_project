package hotline_zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//This class will describe zombie behavior (movement, etc.)
public class Zombie extends Object
{
	//Player player;
	GameObjectHandler oHandler;
	public Zombie(int x, int y, Object_Type type, GameObjectHandler oHandler)
	{
		super(x, y, type);
		this.oHandler = oHandler;
		
	}
	
	public void tick() //Moves the object over each frame
	{
		x += xVelocity;
		y += yVelocity;
		
		hasCollided();
		
		for(int i = 0; i < oHandler.objectList.size(); i++)
		{
			Object temp = oHandler.objectList.get(i);
			
			if(temp.getType() == Object_Type.Player )
			{
	
				//Calculate Distance from Zombie:
				//to left of the player
				double L_Distance = Math.abs(this.x + 25 - temp.x);
				//to right the player
				double R_Distance = Math.abs(temp.x + 24 - this.x);
				//above the player
				double U_Distance = Math.abs(this.y - temp.y + 25);
				//below the player
				double D_Distance = Math.abs(this.y - temp.y); 
				
				 if(L_Distance <= 3)
				{
					xVelocity = 1;
					L_Distance++;
				}
				else if(R_Distance <= 3)
				{
					xVelocity = -1;
					R_Distance--;
				} 
			    if(U_Distance <= 3 )
				{
					yVelocity = 1;
					U_Distance++;
				}  
				else if(D_Distance <= 3)
				{
					yVelocity = -1;		
					D_Distance++;
				} 
				
			}
		}
	
	}
	private void hasCollided() //Keeps the zombie from clipping through walls and the player
	{
		for(int i = 0; i < oHandler.objectList.size(); i++)
		{
			Object temp = oHandler.objectList.get(i);
			
			if(temp.getType() == Object_Type.Block && (this.getBounds().intersects(temp.getBounds())))
			{
				x += xVelocity * -1;
				y += yVelocity * -1;
			}
			
			if(temp.getType() == Object_Type.Player && (this.getBounds().intersects(temp.getBounds())))
			{
				x += xVelocity * -1;
				y += yVelocity * -1;
			}
		}
	}
	
	public void render(Graphics g) //Renders the object again each frame
	{
		g.setColor(Color.GREEN);
		g.fillRect(x, y, 25, 25);
	}
	
	public Rectangle getBounds() //We don't need this now but we will for object collision
	{
		return new Rectangle(x, y, 25, 25);
	}
	
}