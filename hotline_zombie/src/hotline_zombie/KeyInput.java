package hotline_zombie;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
//import javax.swing.JFrame;
//import java.awt.event.*;

//This class describes player movement
public class KeyInput extends KeyAdapter
{
	private GameObjectHandler goh;
	
	private Player player;
	
	public KeyInput(GameObjectHandler goh) {
	    //super();
	    this.goh = goh;
	    //this.player = p;
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
			goh.setUp(true);
			break;
		case 's':
		case 'S':
			goh.setDown(true);
			break;
		case 'd':
		case 'D':
			goh.setRight(true);
			break;
		case 'a':
		case 'A':
			goh.setLeft(true);
			break;
		}
	}
	
	// Handle the key released event from the text field. 
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w':
		case 'W':
			goh.setUp(false);
			break;
		case 's':
		case 'S':
			goh.setDown(false);
			break;
		case 'd':
		case 'D':
			goh.setRight(false);
			break;
		case 'a':
		case 'A':
			goh.setLeft(false);
			break;
		}		
	}
}
