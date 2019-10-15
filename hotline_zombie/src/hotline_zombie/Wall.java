package hotline_zombie;
import java.util.ArrayList;

public class Wall
{
	/* Wild idea: have an ability where you can walk through walls. */
	private int x1, y1, x2, y2, thickness;
	private int ulx, uly, lrx, lry; /* lower right x, upper left x */
	public Wall(int x1, int y1, int x2, int y2, int thickness) {
		
	}
	
	void setX1(int x1) {
		this.x1 = x1;
	}
	void setY1(int y1) {
		this.y1 = y1;
	}
	void setX2(int x2) {
		this.x2 = x2;
	}
	void setY2(int x2) {
		this.x2 = x2;
	}
	
	boolean touching(int x, int y) {
		return (x >= ulx && y >= uly) || (x <= lrx && y <+ lry);   
	}
}
