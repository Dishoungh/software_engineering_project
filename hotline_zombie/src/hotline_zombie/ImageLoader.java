package hotline_zombie;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//This class will load in our images for our levels
public class ImageLoader
{
	private BufferedImage image;
	
	public BufferedImage loadImage(String path)
	{
		try
		{
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
}
