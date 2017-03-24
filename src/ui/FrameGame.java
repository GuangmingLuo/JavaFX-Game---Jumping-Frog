package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import entity.User;
import main.Main;


public class FrameGame extends JFrame{
	
	 //  Define the MainApp.
	 private Main mainApp;
	 
	 //  Create a new FrameGame.
	 FrameGame FG;
	 
	 //  Create a new User.
	 User user;
	 
	 private static final long serialVersionUID = 1L;
	 
	 //	 Create a new Main Panel to playing the game
	 PanelGame game = new PanelGame();
	 
	 //  Define the new Buttons
	 JButton start = new JButton("Start");  
     JButton pause = new JButton("Pause");
     JButton stop = new JButton("Stop");
     JButton Exit = new JButton("Exit");
     
    
     // Set the variable of the pause button.
     private int Pause = 0;
     
	public FrameGame() {
		// Set Title
		this.setTitle("Jumping Frog");

		// Set Default close
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Do not change size
		this.setResizable(false);

		// Set Center
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension Screen = toolkit.getScreenSize();
		int w = (Screen.width - 1200) / 2;
		int h = (Screen.height - 700) / 2;

		// set Button
		jButtonPerformed(null);

		// set windows size
		this.setSize(1200, 700);

		// set Default Panel
		game.setLevel(1);

		// game.moveCloud();
		this.setContentPane(game);
		this.setLocation(w, h);

		// set the current User to panel
		game.setUser(user);
		game.setMainApp(mainApp);

	}
	
	/*
	 * Add the dynamic Listener to the JPanel
	 * Listen to the click and send the data back to the panel to control the game.
	 */

	class dynmaicBtnListener implements java.awt.event.ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Exit from this software when click.
			if (e.getSource() == Exit) {
				System.exit(0);
			}

			// Start the game when clicked.
			if (e.getSource() == start) {
				game.setStart(true);
			}

			// First time clicked, the game will pause.pause = 1
			// Second time clicked, the game will restart.pause = 0
			if (e.getSource() == pause) {
				if (Pause == 1) {
					game.setPause(2);
					Pause = 0;
				} else {
					game.setPause(1);
					Pause = 1;
				}

			}
		}
	}
 
     /*
      * Set the current user to the game,store the grade in the user.
      */
     public void setUser(User users){
    	 this.user = users;
    	 game.setUser(user);
     }
	     
     /*
      * Set the current FrameGame to the game.
      */
     public void getFG(FrameGame fg){
    	 this.FG = fg;
     }
     
     /*
      * Set the current MainApp to the game.
      */
     public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
      }
    
    /*
     * Set the button of the game
     */
    private void jButtonPerformed(java.awt.event.ActionEvent evt) {   
    	
    	// Add the listener to the button
        start.addActionListener(new dynmaicBtnListener ());  
        pause.addActionListener(new dynmaicBtnListener ());  
        Exit.addActionListener(new dynmaicBtnListener ());  
            
        // Add the button to the JPanel with the Box format.
        game.add(start);  
        game.add(Box.createRigidArea(new Dimension(80, 1200)));
        game.add(Exit);
        game.add(Box.createRigidArea(new Dimension(80, 1200))); 
        game.add(pause);
        game.add(Box.createRigidArea(new Dimension(80, 1200))); 
        game.revalidate();   
    } 
	    	   
}
	