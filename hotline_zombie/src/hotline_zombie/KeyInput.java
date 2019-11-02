package hotline_zombie;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.event.*;

//This class describes player movement
public class KeyInput extends JFrame implements KeyListener
{
	private GameObjectHandler goh;
	
	public KeyInput(GameObjectHandler goh) {
	    super();
	    this.goh = goh;
	}
	
	
	/** Handle the key typed event from the text field. */
	public void keyTyped(KeyEvent e) {
	}
	
	/** Handle the key pressed event from the text field. */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w':
		case 'W':
			goh.isUp();
		case 's':
		case 'S':
			goh.isDown();
		case 'd':
		case 'D':
			goh.isRight();
		case 'a':
		case 'A':
			goh.isLeft();
		}
	}
	
	/** Handle the key released event from the text field. */
	public void keyReleased(KeyEvent e) {
	}
}


