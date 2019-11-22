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
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
import java.io.IOException; 

public class Game extends Canvas implements Runnable
{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean gameRunning = false; 						//Checks to see if a thread is already running (Thread-Safety feature)
	
	@SuppressWarnings("unused")
	private	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //This dimension variable is here so we can automatically adjust the frame to be the size of the screen
	
	private Thread t; 									//Sets up a thread to be used
	
	private GameObjectHandler oHandler; 					//Object handler is here
	
	private Player player;                             //Player object is here
	
	private BufferedImage level = null;                //This is our image loader
	
	private Camera camera;                             //Our camera (or user view). This is different from view
													   //Provides the JFrame panel while camera allows us to give us the view of the objects in our panel.

	//Starts the view panel
	public Game()  throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{

		new View(this, "Hotline Zombie", screenSize.width, screenSize.height); //Uncomment this if we want to do fullscreen
		//new View(this, "Hotline Zombie", 1600, 900);

		oHandler = new GameObjectHandler();             //Initializes an Object Handler to handle our objects in the game
		
		//Sets up the camera
		camera = new Camera(600, 500);
		
		//Loads the level and sets the player location
		ImageLoader loader = new ImageLoader();
		level = loader.loadImage("/level1MapShrinked.png"); //We will change the name of this level. It's just a test level for now.
		loadLevel(level);
		
		//Adds keylistener for the key input class
		this.addKeyListener(new KeyInput(player));               //Uses the object handler to listen in on key inputs for the player
		
		//Adds mouselistener and mousemotionlistener for the mouse input class
		this.addMouseListener(new MouseInput(oHandler, camera, player));          //Uses the object handler and the camera to listen on mouse inputs
		this.addMouseMotionListener(new MouseInput(oHandler, camera, player));    //Adds motion listener of the mouse to rotate character models accordingly
		
		AudioPlayer.filePath = "assets/testingSoundtrack.wav";
		AudioPlayer ap;
		ap = new AudioPlayer();
		ap.play();			

		start(); 										//Starts the game
	}
	
	//Threading Functions Start Here
	private void start() //Starts a thread
	{
		if(gameRunning)
		{
			return;
		}
		else
		{
			gameRunning = true;
			t = new Thread(this);
			t.start();
		}
	}
	
	private void stop() //Stops a thread
	{
		if(!gameRunning)
		{
			return;
		}
		else
		{
			gameRunning = false;
			try
			{
				t.join();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void run() //Runs the thread (this allows us to run the program)
	{
		this.requestFocus();
		long last = System.nanoTime();
		double tickRate = 60.0;   
		double ns = 1000000000 / tickRate;
		double difference = 0; //In terms of nanoseconds	
		long timer = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int numFrames = 0;
		while(gameRunning)
		{
			long now = System.nanoTime();
			difference = difference + ((now - last) / ns);
			last = now;
			while (difference >= 1)
			{
				tick();			//After some time has passed, start a new frame
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

	public synchronized void tick() //Allows us to move each object after a frame has been started one at a time
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
	
	public synchronized void render() //Allows us to graphically render the objects back each frame
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

		g.fillRect(0, 0, screenSize.width, screenSize.height); //Get rid of this if we want to do fullscreen
		//g.fillRect(0, 0, 1600, 900);
		
		g2.translate(-camera.getX(), -camera.getY()); //Everything between the 2 translate statements will translate all objects
		oHandler.render(g);    //Object handler renders all the objects inside
		g2.translate(camera.getX(),camera.getY());
		//----------------------------------------------- Camera Movement Ends Here	
		g.dispose();
		buffer.show();			//Makes the updated view visible
		
	}
	//Threading Functions Stop Here
	
	
	//Loads the level and scans the level blueprint and places objects accordingly
	private synchronized void loadLevel(BufferedImage img)
	{
		int width = img.getWidth();
		int height = img.getHeight();
		int moveIncr = 5;
		
		for(int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				int pixel = img.getRGB(x, y);
				int red = (pixel >> 16) & 0xff; 
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if (red == 255 && green == 0 && blue == 0) //Places a block whenever it detects a red pixel block
				{
					oHandler.addObject(new Block(x*32, y*32, Object_Type.Block));
				}
				
				
				if (red == 0 && green == 255 && blue == 0) //Places a zombie object whenever a green pixel block is detected
				{
					oHandler.addObject(new Zombie(x*32, y*32, Object_Type.Zombie, oHandler));
				}

				if (red == 0 && green == 0 && blue == 255) //Places the player wherever the blue pixel block is
				{
					player = new Player(x*32, y*32, moveIncr, Object_Type.Player, oHandler);
					oHandler.addObject(player);
				}
			}
		}
	}
	
	//Main Function Here
	public static void main(String[] args){
		//Starts the game here
		try {
			new Game();
		}
		catch (IOException e) {
			System.out.println("Your shit's fucked up.");
		}
		catch (LineUnavailableException e) {
			System.out.println("Your shit's really fucked up.");
		}
		catch (UnsupportedAudioFileException e) {
			System.out.println("Your shit's really, really fucked up.");
		}
		
	}

}