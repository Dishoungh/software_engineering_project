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
import java.awt.image.BufferStrategy;
import java.awt.Toolkit;

public class Game extends Canvas implements Runnable
{	
	private boolean run = false; 						//Checks to see if a thread is already running (Thread-Safety feature)
	
	private	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //This dimension variable is here so we can automatically adjust the frame to be the size of the screen
	
	private Thread t; 									//Sets up a thread to be used
	
	private Object_Handler oHandler; 					//Object handler is here

	//Starts the view panel
	public Game() 
	{
		//new View(this, "Hotline Zombie", screenSize.width, screenSize.height); Uncomment this if we want to do fullscreen
		new View(this, "Hotline Zombie", 1600, 900);
		start(); 										//Starts the game
		oHandler = new Object_Handler();
		
		//Add objects here (for now) -- We will add/delete/move objects around here a lot
		oHandler.addObject(new Zombie(100, 100)); 		//Adds Zombie #1
		oHandler.addObject(new Zombie(200, 100)); 		//Adds Zombie #2
		oHandler.addObject(new Player(50, 900-100)); 	//Adds player
		//-------------------------------------------------------------------------------
		
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
		double numTicks = 60.0; // We want it to be 60fps
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
				//updates++;
				difference--;
			}
			render();
			numFrames++; //Increments the number of frames
			
			if ((System.currentTimeMillis() - timer) > 1000)
			{
				timer += 1000;
				numFrames = 0;
				//updates = 0;
			}
		}
		stop();
	}

	public void tick() //Allows us to move each object after a frame has been started one at a time
	{
		oHandler.tick();
	}
	
	public void render() //Allows us to graphically render the objects back each frame
	{
		BufferStrategy buffer = this.getBufferStrategy(); //We will create a buffer to essentially help recreate our view each frame
		if (buffer == null) //If there is no buffer, the game will create one for us of a size of 3
		{
			this.createBufferStrategy(3); //The whole point of us creating this buffer strategy is for performance
			return;								//Without this buffer strategy, our game will probably be slower.
		}
		
		Graphics g = buffer.getDrawGraphics();
		g.setColor(Color.LIGHT_GRAY);
		//g.fillRect(0, 0, screenSize.width, screenSize.height); Get rid of this if we want to do fullscreen
		g.fillRect(0, 0, 1600, 900);
		
		oHandler.render(g);    //Object handler renders all the objects inside
		
		g.dispose();
		buffer.show();			//Makes the updated view visible
		
	}
	//Threading Functions Stop Here
	
	//Main Function Here
	public static void main(String[] args)
	{
		//Starts the game here
		new Game();
	}

}

