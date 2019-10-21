package hotline_zombie;


//This describes the entire map level
import javax.swing.JFrame;
import java.awt.Dimension;

public class Map extends JFrame
{
	//This will set up the JFrame (This may or may not need to be changed)
	public Map(Game game, String name, int width, int height)
	{
		JFrame frame = new JFrame(name);
		Dimension mapSize = new Dimension(width, height);
		frame.setSize(mapSize);
		frame.setMinimumSize(mapSize);
		frame.setMaximumSize(mapSize);
		frame.add(game);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}