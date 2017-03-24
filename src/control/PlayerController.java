package control;

import ui.PanelGame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener{
	
	//  Create new Panel game to set Char to it.
	PanelGame pg;
	
	//	Define KeyInput to send the Char.
	char keyInput = 0;
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyInput = e.getKeyChar();
		//	Get all the input from the keyboard

		if(keyInput >= 'A' & keyInput <= 'z'){
			this.pg.setInput(keyInput);
		}
		// Get Char from keyboard,check it and send it to the panel(GUI).	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub	
	}
	
	/*
	 * Method to get the char. 
	 */
	public char getKeyInput(){
		return keyInput;
	}
	
	/*
	 * Method to set the specific panel.
	 */
	public void setPanelGame(PanelGame pg){
		this.pg = pg;
	}
}
