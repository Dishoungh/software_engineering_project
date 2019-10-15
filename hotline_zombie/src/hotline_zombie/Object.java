package hotline_zombie;
import java.awt.Rectangle;
import java.awt.Graphics;

//This will characterize ANY object we put into the game (player and zombies)
public abstract class Object 
{
	protected int x, y;
	protected float xVelocity, yVelocity;
	
	public Object(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds(); //We might need this to check bounds for collision

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}

	public float getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
	
}
