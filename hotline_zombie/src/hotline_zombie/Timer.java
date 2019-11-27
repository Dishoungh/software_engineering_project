package hotline_zombie;

//The whole purpose of this timer class is so I can have it start a thread to count down the number of seconds and pause while the main game is still running
//This prevents the player from getting killed instantaneously with above 1 health. We needed the player to have a brief invincibility period. 
public class Timer implements Runnable
{
	@SuppressWarnings("unused")
	private boolean stop;
	private Thread timerThread;
	private int seconds;
	private Player player;
	
	//Constructor
	public Timer(Player player)
	{
		seconds = 2;
		this.player = player;
		start();
	}
	
	private synchronized void start()
	{
		stop = false;
		player.setInvincibility(true);
		timerThread = new Thread(this);
		timerThread.start();	
	}
	
	public synchronized void stop()
	{
		stop = true;
		player.setInvincibility(false);	
		try
		{
			timerThread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void run()
	{
		while(seconds > 0)
		{
			seconds--;
			
			if(seconds == 0)
			{

			}
			else
			{
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					System.err.println("Error: " + e.getMessage());
				}
			}
		}
		stop();
	}
}
