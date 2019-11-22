package hotline_zombie;

//This class describes the camera behavior. It will follow the player's movement so we can traverse the map
public class Camera
{
	//Change these to float or int if something goes wrong
	private float x, y;
	
	//Constructor
	public Camera(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	//Allows us to translate each object on the screen in accordance to player movement
	public synchronized void tick(Object o)
	{
		x += ((o.getX() - x) - 1900/2) * 0.05f;
		y += ((o.getY() - y) - 600/2) * 0.05f;
		
		
		//These statements below will stop our camera from panning outside the map (CHANGE THESE VALUES IN ACCORDANCE TO LEVEL SIZE SO THE CAMERA BOUNDARIES AND THE MAP BOUNDARIES WILL MATCH)
		if(x <= 0)
		{
			x = 0;
		}
		
		if(x >= 1000000)
		{
			x = 1000000;
		}
		
		if(y <= 0)
		{
			y = 0;
		}
		
		if(y >= 1000000)
		{
			y = 1000000;
		}
	}

	//Getters and Setters for x and y
	public synchronized float getX() {
		return x;
	}

	public synchronized void setX(float x) {
		this.x = x;
	}

	public synchronized float getY() {
		return y;
	}

	public synchronized void setY(float y) {
		this.y = y;
	}
	
	
}
