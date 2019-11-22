package hotline_zombie;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
//import javax.swing.JFrame;
//import java.awt.event.*;

//This class describes player movement
public class KeyInput extends KeyAdapter
{
	
	private Player player;
	
	public KeyInput(Player p) {
	    super();
	    this.player = p;
	}
	
	/*
	//Handle the key typed event from the text field.
	public void keyTyped(KeyEvent e) {
	}
    */
	
	/** Handle the key pressed event from the text field. */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w':
		case 'W':
			player.setUp(true);
			break;
		case 's':
		case 'S':
			player.setDown(true);
			break;
		case 'd':
		case 'D':
			player.setRight(true);
			break;
		case 'a':
		case 'A':
			player.setLeft(true);
			break;
		case 'p':
		case 'P':
			player.setPause(!player.getPaused());
		
		}
	}
	
	// Handle the key released event from the text field. 
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w':
		case 'W':
			player.setUp(false);
			break;
		case 's':
		case 'S':
			player.setDown(false);
			break;
		case 'd':
		case 'D':
			player.setRight(false);
			break;
		case 'a':
		case 'A':
			player.setLeft(false);
			break;
		}
	}
}
