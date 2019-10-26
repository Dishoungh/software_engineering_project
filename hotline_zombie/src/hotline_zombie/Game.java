/* Team Name: ?
 * Team Members: Dishoungh White II, Alaina Edwards, Valdimar Sigurdsson, Gavin Glenn
 * Date Created: October 13, 2019
 * 
 * 
 */

//This is the main class of our game
package hotline_zombie;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;

public class Game extends Canvas implements Runnable
{	
	private boolean run = false; 						//Checks to see if a thread is already running (Thread-Safety feature)
	
	private	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //This dimension variable is here so we can automatically adjust the frame to be the size of the screen
	
	private Thread t; 									//Sets up a thread to be used
	
	private Object_Handler oHandler; 					//Object handler is here
	
	private BufferedImage level = null;                //This is our image loader
	
	private Camera camera;                             //Our camera (or user view)

	//Starts the view panel
	public Game() 
	{
		//new view(this, "Hotline Zombie", screenSize.width, screenSize.height); Uncomment this if we want to do fullscreen
		new View(this, "Hotline Zombie", 1600, 900);
		start(); 										//Starts the game
		oHandler = new Object_Handler();
		
		//this.addKeyListener(new KeyInput(oHandler)); //Uses the object handler to listen in on key inputs
		
		//Sets up the camera
		camera = new Camera(0, 0);
		
		//Loads the level
		ImageLoader loader = new ImageLoader();
		level = loader.loadImage("/test_level.png"); //We will change the name of this level. It's just a test level for now.
		
		loadLevel(level);
		
	}
	
	//Threading Functions Start Here
	private void start() //Starts a thread
	{
		run = true;
		t = new Thread(this);
		t.start();
	}
	
	private void stop() //Stops a thread
	{
		run = false;
		try
		{
			t.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run() //Runs the thread (this allows us to run the program)
	{
		this.requestFocus();
		long last = System.nanoTime();
		double numTicks = 60.0;
		double ns = 1000000000 / numTicks;
		double difference = 0; //In terms of nanoseconds	
		long timer = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int numFrames = 0;
		while(run)
		{
			long now = System.nanoTime();
			difference = difference + ((now - last) / ns);
			last = now;
			while (difference >= 1)
			{
				tick();			//After some time has passed, start a new frame essentially
				difference--;
			}
			render();
			numFrames++; //Increments the number of frames
			
			if ((System.currentTimeMillis() - timer) > 1000)
			{
				timer += 1000;
				numFrames = 0;
			}
		}
		stop();
	}

	public void tick() //Allows us to move each object after a frame has been started one at a time
	{
		//Now we need to find the player in the objectList and have the camera update in accordance to its movement
		for (int i = 0; i < oHandler.objectList.size(); i++)
		{
			if(oHandler.objectList.get(i).getType() == Object_Type.Player)
			{
				camera.tick(oHandler.objectList.get(i));
			}
		}
		
		oHandler.tick();
	}
	
	public void render() //Allows us to graphically render the objects back each frame
	{
		BufferStrategy buffer = this.getBufferStrategy(); //We will create a buffer to essentially help recreate our view each frame
		if (buffer == null) //If there is no buffer, the game will create one for us of a size of 3
		{
			this.createBufferStrategy(3); 
			return;								
		}
		
		//----------------------------------------------- Camera Movement Starts Here
		Graphics g = buffer.getDrawGraphics();
		//We will create a 2D graphics object here and use Graphics g for Graphics2D g2. The purpose of this is so we can easily translate our objects into our camera.
		Graphics2D g2 = (Graphics2D)g;
		
		g.setColor(Color.LIGHT_GRAY);
		//g.fillRect(0, 0, screenSize.width, screenSize.height); Get rid of this if we want to do fullscreen
		g.fillRect(0, 0, 1600, 900);
		
		g2.translate(-camera.getX(), -camera.getY()); //Everything between the 2 translate statements will translate all objects
		oHandler.render(g);    //Object handler renders all the objects inside
		g2.translate(camera.getX(),camera.getY());
		//----------------------------------------------- Camera Movement Ends Here	
		g.dispose();
		buffer.show();			//Makes the updated view visible
		
	}
	//Threading Functions Stop Here
	
	
	//Load the level and scans the level blueprint and places objects accordingly
	private void loadLevel(BufferedImage img)
	{
		int width = img.getWidth();
		int height = img.getHeight();
		
		for(int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				int pixel = img.getRGB(x, y);
				int red = (pixel >> 16) & 0xff; 
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if (red == 255) //Places a block whenever it detects a red pixel block
				{
					oHandler.addObject(new Block(x*32, y*32, Object_Type.Block));
				}
				
				if (blue == 255) //Places the player wherever the blue pixel block is
				{
					oHandler.addObject(new Player(x*32, y*32, Object_Type.Player, oHandler));
				}
				
				if (green == 255) //Places a zombie object whenever a green pixel block is detected
				{
					oHandler.addObject(new Zombie(x*32, y*32, Object_Type.Zombie));
				}
			}
		}
	}
	
	//Main Function Here
	public static void main(String[] args){
		//Starts the game here
		new Game();
	}

}