package hotline_zombie;


//This describes what the player will see
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class View extends JFrame
{
	public JFrame frame;
	//This will set up the JFrame (This may or may not need to be changed)
	public View(Game game, String name, int width, int height)
	{
		frame = new JFrame(name);
		Dimension screenSize = new Dimension(width, height);
		frame.setSize(screenSize);
		frame.setMinimumSize(screenSize);
		frame.setMaximumSize(screenSize);
		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
}
