package hotline_zombie;

//This class describes the camera behavior. It will follow the player's movement so we can traverse the map
public class Camera
{
	//Change these to float or int if something goes wrong
	private float x, y;
	
	public Camera(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	//Allows us to translate each object on the screen in accordance to player movement
	public void tick(Object o)
	{
		x += ((o.getX() - x) - 1000/2) * 0.05f;
		y += ((o.getY() - y) - 500/2) * 0.05f;
		
		
		//These statements below will stop our camera from panning outside the map (CHANGE THESE VALUES IN ACCORDANCE TO LEVEL SIZE SO THE CAMERA BOUNDARIES AND THE MAP BOUNDARIES WILL MATCH)
		if(x <= 0)
		{
			x = 0;
		}
		
		if(x >= 1000)
		{
			x = 1000;
		}
		
		if(y <= 0)
		{
			y = 0;
		}
		
		if(y >= 500)
		{
			y = 500;
		}
	}

	//Getters and Setters for x and y
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
}
