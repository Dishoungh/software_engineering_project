/* Team Name: ?
 * Team Members: Dishoungh White II, Alaina Edwards, Valdimar Sigurdsson, Gavin Glenn
 * Date Created: October 13, 2019
 * 
 * 
 */

//This is the main class of our game
package hotline_zombie;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.TextArea;
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
import java.io.IOException; 
import javax.swing.JLabel;


@SuppressWarnings("unused")
public class Game extends Canvas implements Runnable
{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean gameRunning = false; 						//Checks to see if a thread is already running (Thread-Safety feature)	
	private boolean audioPaused = false;
	private boolean gameWon = false;
	
	private	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //This dimension variable is here so we can automatically adjust the frame to be the size of the screen
	private Thread t; 									//Sets up a thread to be used
	private GameObjectHandler oHandler; 				//Object handler is here
	private Player player;                             //Player object is here
	
	private BufferedImage level = null;                //This is our image loader
	// Our camera (or user view). This is different from view
	// View provides the JFrame panel while camera allows us to give us the view of the objects in our panel.
	private Camera camera;                             
	private int playerIndex; // Index for player.
	private AudioPlayer audioPlayer;
	private View view; 
	private TextArea pauseTextArea;
	private JLabel pauseLabel;
	private int gameWidth, gameHeight;
	private Graphics g;
	private Graphics2D g2;

	//Starts the view panel
	public Game()  throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		// Change these for a different resolution.
		gameWidth = screenSize.width;
		gameHeight = screenSize.height;
		view = new View(this, "Hotline Zombie", gameWidth, gameHeight); 

		oHandler = new GameObjectHandler();             //Initializes an Object Handler to handle our objects in the game
		
		//Sets up the camera
		camera = new Camera(600, 500);
		
		//Loads the level and sets the player location
		ImageLoader loader = new ImageLoader();
//		backgroundMap = loader.loadImage("/backgroundMap.png");
		level = loader.loadImage("/level1Map.png");
		loadLevel(level);
		
		//Adds keylistener for the key input class
		this.addKeyListener(new KeyInput(this, player));               //Uses the object handler to listen in on key inputs for the player
		
		//Adds mouselistener and mousemotionlistener for the mouse input class
		this.addMouseListener(new MouseInput(oHandler, camera, player));          //Uses the object handler and the camera to listen on mouse inputs
		this.addMouseMotionListener(new MouseInput(oHandler, camera, player));    //Adds motion listener of the mouse to rotate character models accordingly
		
		AudioPlayer.filePath = "assets/testingSoundtrack.wav";
		audioPlayer = new AudioPlayer(true);
		audioPlayer.play();			
		
		pauseLabel = new JLabel("( ͡° ͜ʖ ͡°) PAUSED ( ͡° ͜ʖ ͡°)");
		
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
		if (gameRunning)
		{
			
			try {
				audioPlayer.stop();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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

		while(gameRunning && player.isAlive())
		{
			long now = System.nanoTime();
			difference = difference + ((now - last) / ns);
			last = now;
	
			if(player.getPaused() == true)
			{
				difference = 0;
				
				if(!audioPaused)
				{
					try {
						audioPlayer.pause();
					}
					catch (Exception ex) {
						System.out.println("Unable to pause audio.");
					}
					audioPaused = true;
				}
			}
			else 
			{
				if(audioPaused)
				{
					try {
						audioPlayer.resumeAudio();
					}
					catch (Exception ex) {
						//System.out.println("Unable to resume audio.");
					}
					
					audioPaused = false;
				}
			}
			
			while (difference >= 1)
			{
				tick();			//Translate all objects
				difference--;
			}
			render();
			
			if(oHandler.getZombieCount() == 0)
			{
				gameWon = true;
			}
			
		}
		render();
		
		stop();
	}

	public synchronized void tick() //Allows us to move each object after a frame has been started one at a time
	{
		//Now we need to find the player in the objectList and have the camera update in accordance to its movement
		camera.tick(oHandler.objectList.get(playerIndex));		
		oHandler.tick();
	}
	
	public synchronized void render() //Allows us to graphically render the objects back each frame
	{
		// We will create a buffer to essentially help recreate our view each frame
		BufferStrategy buffer = this.getBufferStrategy(); 
		if (buffer == null) //If there is no buffer, the game will create one for us of a size of 3
		{
			createBufferStrategy(3); 
			return;								
		}
		
		//----------------------------------------------- Camera Movement Starts Here
		g = buffer.getDrawGraphics();
		//We will create a 2D graphics object here and use Graphics g for Graphics2D g2. The purpose of this is so we can easily translate our objects into our camera.
		g2 = (Graphics2D)g;
		
		g.setColor(Color.LIGHT_GRAY);

		g.fillRect(0, 0, gameWidth, gameHeight); //Get rid of this if we want to do fullscreen
		//g.fillRect(0, 0, 1600, 900);
		
		g2.translate(-camera.getX(), -camera.getY()); //Everything between the 2 translate statements will translate all objects
		oHandler.render(g);    //Object handler renders all the objects inside
		g2.translate(camera.getX(),camera.getY());
		//----------------------------------------------- Camera Movement Ends Here	
		
		//------------------------------------------- Display Health Bar
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 200, 32);
		if(player.getHealth() >= 4)
		{
			g.setColor(Color.GREEN);
		}
		else if(player.getHealth() >= 2)
		{
			g.setColor(Color.YELLOW);
		}
		else 
		{
			g.setColor(Color.RED);
		}
		g.fillRect(5, 5, player.getHealth()*40, 32);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, 200, 32);
		
		
		//----------------------------------------- Display Zombie Count
		g.setColor(Color.BLACK);
		g.drawString("Zombies Left: " + oHandler.getZombieCount(), 9, 50);
		
		
		//Print Death Screen
		if(!player.isAlive())
		{
			g.setColor(Color.BLACK);
			Font font = new Font("Verdana", Font.BOLD, 60);
			g.setFont(font);
			g.drawString("You are Dead!", (screenSize.width / 2) - 150, screenSize.height/2);
		}
		else 
		{
			//Print Pause Menu if paused
			if(player.getPaused() == true)
			{
				g.setColor(Color.BLACK);
				Font font = new Font("Verdana", Font.BOLD, 60);
				g.setFont(font);
				g.drawString("PAUSED", (screenSize.width / 2) - 150, screenSize.height/2);
			}
			
			//Print Victory Menu
			if(gameWon == true)
			{
				g.setColor(Color.BLACK);
				Font font = new Font("Verdana", Font.BOLD, 60);
				g.setFont(font);
				g.drawString("You Win!", (screenSize.width / 2) - 150, screenSize.height/2);
			}
		}
		
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
		int tileLength = 4;
		for(int x = 0; x < width; x += tileLength)
		{
			for (int y = 0; y < height; y += tileLength)
			{
				int pixel = img.getRGB(x, y);
				int red = (pixel >> 16) & 0xff; 
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if (red == 255 && green == 0 && blue == 0) //Places a block whenever it detects a red pixel block
				{
					oHandler.addObject(new Block(x/tileLength*32, y/tileLength*32, Object_Type.Block));
				}
				
				
				if (red == 0 && green == 255 && blue == 0) //Places a zombie object whenever a green pixel block is detected
				{	
					oHandler.addObject(new Zombie(x/tileLength*32, y/tileLength*32, Object_Type.Zombie, oHandler));
					oHandler.incrZombies();
					
				}

				if (red == 0 && green == 0 && blue == 255) //Places the player wherever the blue pixel block is
				{
					player = new Player(x/tileLength*32, y/tileLength*32, moveIncr, Object_Type.Player, oHandler);
					oHandler.addObject(player);
					this.playerIndex = oHandler.objectList.size() - 1;
				}
			}
		}
	}
	public void pauseGame() {
		if (gameRunning) {
			stop();
			try {
				audioPlayer.pause();
			}
			catch (Exception ex) {
				System.out.println("Unable to pause audio.");
			}
			view.frame.add(pauseLabel, BorderLayout.CENTER);
		    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		    g.drawString("PAUSED", gameWidth/2, gameHeight/2);
//	        view.frame.setSize(400,400);  
//	        view.frame.setLayout(null);  
//	        view.frame.setVisible(true);  			
		}
		else {
			start();
			try {
				audioPlayer.resumeAudio();
			}
			catch (Exception ex) {
				System.out.println("Unable to resume audio.");
			}
			
//			view.frame.remove(pauseLabel);;
		}
	}
	
	//Main Function Here
	public static void main(String[] args){
		//Starts the game here

		try {
			new Game();
		}
		catch (IOException e) {
			System.out.println("Sound file not found.");
		}
		catch (LineUnavailableException e) {
			System.out.println("Line unavailable for sound file.");
		}
		catch (UnsupportedAudioFileException e) {
			System.out.println("Unsupported audio file.");
		}
		
	}

}