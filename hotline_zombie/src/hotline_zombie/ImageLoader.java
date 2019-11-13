package hotline_zombie;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

//This class will load in our images for our levels
public class ImageLoader
{
	private BufferedImage image;
	
	//Loads the image given the path
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
	
	//Rotates an image given an angle and an image
	public BufferedImage rotImage(int degrees, BufferedImage img) {
		double rotationRequired = Math.toRadians(degrees);
        double locationX = img.getWidth() / 2;
        double locationY = img.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);         
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        op.filter(img, newImage);
        return(newImage);
	}
}
