package ui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


import control.PlayerController;
import entity.User;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.Main;
import persistence.UserMapper;
import persistence.WordMapper;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;



public class PanelGame extends JPanel{
	
	// To solve the conflict between different edition.
	private static final long serialVersionUID = 1L;
	
	// Set MainApp
	public Main mainApp;
	
	// Initial the level of the game.
	int level = 1;
	
	// Initial the Y position of the cloud and word with an array.
	int[] y = {110,510,910,110,510,910,110,510,910}; 
	
	// Create a container to use the words.
	String[] words = new String[10];
	
	// Create a variable to use current user.
	User user;
	
	// Initial the speed of the cloud.
	int speed  = 1 ;
	
	// Define the word so that we can get the word from database.
	WordMapper wp = WordMapper.UNIQUEINSTANCE;
	
	// Define the user as a current user so that we can store the grade and result.
	UserMapper um = UserMapper.UNIQUEINSTANCE;
	
	// Define a PlayerController so that we can input the char to the panel.
	PlayerController pc = new PlayerController();
	
	// Create a variable to control the stage of the cloud.
	int stage = 0;
	
	// Create a boolean "check" to check the character which is spelling right.
	boolean check = false;
	
	// Create a variable to get the Char input.
	char input = '0';
	
	// Create a variable to check the position of the word.
	int wordindex = 1;
	
	// Create a variable to check the current cloud number.
	int NrOfCloud = 0;
	
	// Create a variable to get the value from the button and control the game.
	int Pause = 0;
	
	// Create a boolean to control the start of the game.
    boolean Start = false;

    // Create a variable to store the grade of the user.
	int grade = 0;
	
	// Create a double to store the time by finish the game with full score.
	double counter = 0;
	
	// Reserve two decimal position of the double
	java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");  
    
	// Create the cloud array(picture).
    Layer[] cloud = new Layer[10];   
    
    // To change the position of the frog when bind is true.
    boolean[] bind = {false,false,false,false,false,false,false,false,false,false}; 
	
    // Initial the position and info of the frog and reserve in an array.
	Layer frog1 = new Layer(75,575,1,"FROG");

    Layer frog2 = new Layer(275,575,2,"FROG");

    Layer frog3 = new Layer(875,575,3,"FROG");

    Layer frog4 = new Layer(1075,575,4,"FROG");
    
    Layer[] frog = {null,frog1,frog2,frog3,frog4};
    
    // Initial a background picture.
    Layer background = null;
    
    // Define the URL to get the background music
    private static final String SOURCE = Main.class.getResource("jump.wav").toExternalForm();

    // Create new sound in an array
    MediaPlayer[] sd = new MediaPlayer[16];
    
	
	public PanelGame(){
		
		// Initial the sound file.
		creatMP();
		
		// Initial the component.
		initialComponent();
		
		// Start the thread
		Thread animationThread = new Thread(new Runnable() {
            public void run() {
            	
                while (true) {
                	
                	// Use a loop and if to jump out the loop when the 
                	// button is clicked second time.  
                	if(Pause == 1){
                		for(int i = 0;i < 2;i++)
                		 {
                			i = 0;
                			if(Pause == 2)
                	          {
                				i = 3;
                	          }
                		 }	
                	}      	
      
                	// The start is true when start button is clicked.
                	if(Start)
                	{		
                	// Calculate the time.
                    counter = counter + 0.01;
                    
                    // Change the position of the cloud.
                	calculateY();
                	
                	// Move frog when answer is right
                	moveFrog();
                	
                	calculateGrade();
                	
                	// Check if the frog is crossing over the boundary.
                	checkCross(getFrogXposition());
  
                	// Refresh
                    repaint();
                    }
                    try {
                    	// Refresh rate(0.01s)
                    	Thread.sleep(10);} catch (Exception ex) {}
                }
            }
        });
        animationThread.start();        
		}
	
 
	
	/*
	 * Calculate the grade. In level 1 with 1 score each cloud.
	 * And level 2 with 2 scores each cloud.
	 */
	public void calculateGrade() {
		switch (level) {
		case 1:
			grade = stage;
			break;
		case 2:
			grade = 4 + stage * 2;
			break;
		case 3:
			grade = 12 + stage * 3;
			break;
		case 4:
			grade = 24 + stage * 4;
			break;
		default:
			break;
		}
	}
	
    /*
     * Create the new sound 
     */
	private void creatMP(){
    	for(int i = 0; i < 16; i++){
    		sd[i] = new MediaPlayer(new Media(SOURCE));
    	}
    }
	

	private void initialComponent(){
		
		pc.setPanelGame(this);
		this.addKeyListener(pc);	
		this.createWord();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {

		this.createBackground();
		this.drawBackground(g);
		this.creatCloud();
		this.drawCloud(g);
		this.drawWord(g);
		this.drawFrog(g);
		this.requestFocus();
	}
	
	/*
	 * Change the position of the cloud.
	 */
	private void calculateY(){
		y[0] = y[0] + speed;
		y[1] = y[1] + speed;
		y[2] = y[2] + speed;
		y[3] = y[3] - speed;
		y[4] = y[4] - speed;
		y[5] = y[5] - speed;
		y[6] = y[6] + speed;
		y[7] = y[7] + speed;
		y[8] = y[8] + speed;
		
		if(y[0] >= 1200){
			y[0]= -180;
			y[6]= -180;
		}
		if(y[1] >= 1200){
			y[1]= -180;
			y[7]= -180;
		}
		if(y[2] >= 1200){
			y[2]= -180;
			y[8]= -180;
		}
		if(y[3] <= -180){
			y[3] =1200;
		}
		if(y[4] <= -180){
			y[4] = 1200;
		}
		if(y[5] <= -180){
			y[5] = 1200;
		}
	}

    private void createBackground(){
    	
		background = new Layer(0,0,level,"BG");
		
	}	
	private void drawBackground(Graphics g){
		
		background.draw(g);
	}
	
	private void creatCloud(){
		cloud[0] = new Layer(110,40,level,"Cloud");
	    cloud[1] = new Layer(y[0],180,level,"Cloud");
	    cloud[2] = new Layer(y[1],180,level,"Cloud");
	    cloud[3] = new Layer(y[2],180,level,"Cloud");
	    cloud[4] = new Layer(y[3],290,level,"Cloud");
	    cloud[5] = new Layer(y[4],290,level,"Cloud");
	    cloud[6] = new Layer(y[5],290,level,"Cloud");
	    cloud[7] = new Layer(y[6],400,level,"Cloud");
	    cloud[8] = new Layer(y[7],400,level,"Cloud");
	    cloud[9] = new Layer(y[8],400,level,"Cloud");
	    
	    for(int i = 9; i>=0; i--){
	    	if(bind[i]){
	    		setFrogPosition(i);
	    	}
	    }
	}	

	private void drawCloud (Graphics g){		
        for(int i = 0; i < 10; i++){
        	cloud[i].draw(g);
        }	    
	}
	
	private void drawFrog(Graphics g){

		for(int i = 1; i < 5; i++){
			frog[i].draw(g);
		}
	    	    
	}
	
	/*
	 * Set the frog which jump through the cloud.
	 */
	private void setFrogPosition(int Level,int xPosition){
		frog[level].setX(xPosition);
		frog[level].setY(40);
		
	}
	
	/*
	 * Get the position of the frog to check the if you lose.
	 */
	private int getFrogXposition(){
		return frog[level].getX();
	}
	
	/*
	 * Make the frog jump.
	 */
	private void setFrogPosition(int nr){
		frog[level].setX(cloud[nr].getX()+80);
		frog[level].setY(cloud[nr].getY()+60);
	}
	
	private void createWord(){
		for(int i = 0; i < 10; i++ ){
			words[i] = wp.getWordRandomly(level);
		}
	}
	
	private void drawWord(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		int fontSize = 20;
		Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
		g2.setFont(f);
		g2.setStroke(new BasicStroke(10f));
		g2.drawString(String.valueOf(grade), 1000, 100);
		g2.drawString(words[0], 190, 100);
		g2.drawString(words[1], y[0] + 80, 240);
		g2.drawString(words[2], y[1] + 80, 240);
		g2.drawString(words[3], y[2] + 80, 240);
		g2.drawString(words[4], y[3] + 80, 350);
		g2.drawString(words[5], y[4] + 80, 350);
		g2.drawString(words[6], y[5] + 80, 350);
		g2.drawString(words[7], y[6] + 80, 460);
		g2.drawString(words[8], y[7] + 80, 460);
		g2.drawString(words[9], y[8] + 80, 460);

		// To change the color when click the right character.
		if (check) {
			for (int a = 0; a <= 9; a++) {
				int h = 0;
				int x = 0;
				if (a == NrOfCloud) {
					g2.setColor(Color.RED);
					if (a > 6) {
						x = y[a - 1] + 80;
						h = 460;
					} else if ((a < 4) && (a > 0)) {
						x = y[a - 1] + 80;
						h = 240;
					} else if (a > 3 && a < 7) {
						h = 350;
						x = y[a - 1] + 80;
					}
					if (wordindex == 1) {
						g2.drawString(String.valueOf(words[a].charAt(0)), x, h);
					} else {
						g2.drawString(words[a].substring(0, wordindex), x, h);
					}
				}
				if (NrOfCloud == 0) {
					if (wordindex == 1) {
						g2.drawString(String.valueOf(words[0].charAt(0)), 190, 100);
					} else {
						g2.drawString(words[0].substring(0, wordindex), 190, 100);
					}
				}
			}
		}
	}
	
	/*
	 * Check if the frog going through the boundary.
	 */
	public void checkCross(int position) {
		if (position >= 1199 || position <= 2) {
			JOptionPane.showConfirmDialog(this, "YOU LOSE.FINAL SCORE IS " + grade, "RESULT", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (user.getMaxGrade() < grade) {
				um.setUserMaxGrade(user, grade);
			}
			System.exit(0);
		}
	}
		
	/*
	 * Get the value of the time when finish all the frog.
	 */
	public void winAndCheckTime(){

		um.setUserMinTime(user, df.format(counter));
		um.setUserMaxGrade(user, 40);
		JOptionPane.showConfirmDialog(this,"YOU WIN.YOU USE  " + df.format(counter) + "SECOND","RESULT", JOptionPane.CLOSED_OPTION,  JOptionPane.INFORMATION_MESSAGE);
	    System.exit(0);	
	}
		

	/*
	 * Check the character of one word.
	 */
	public void moveFrog() {

		if (input != '0') {
			switch (stage) {
			case 0:
				// If we choose the cloud, next time we will jump into this if.
				if (NrOfCloud > 6) {
					char[] lists = new char[words[NrOfCloud].length()];
					lists = this.changeStringToChar(words[NrOfCloud]);
					// Then we check the rest char
					if (wordindex < words[NrOfCloud].length()) {
						if (lists[wordindex] == input) {

							wordindex++;
						}
					}

					// We check until all character is same.
					if (wordindex == words[NrOfCloud].length()) {
						// Jump the frog
						bind[NrOfCloud] = true;
						// Playing the sound
						sd[(level - 1) * 4 + 0].play();
						// Change the stage
						this.setStage(1);
						// Initial the Cloud Number
						NrOfCloud = 0;
						// Initial the wordIndex
						wordindex = 1;
						// Initial the check
						check = false;
						break;
					}
				} else {
					// Choose the cloud from the first stage
					for (int a = 7; a < 10; a++) {
						// Get the first char from the word
						char[] list = new char[words[a].length()];
						list = this.changeStringToChar(words[a]);

						// Check if it is equal between the char and input
						if (list[0] == input) {
							NrOfCloud = a;
							check = true;
						}
					}
				}
				break;
			case 1:
				if (NrOfCloud > 3) {
					char[] lists = new char[words[NrOfCloud].length()];
					lists = this.changeStringToChar(words[NrOfCloud]);
					if (wordindex < words[NrOfCloud].length()) {
						if (lists[wordindex] == input) {
							{
								wordindex++;
							}
						}
					}
					if (wordindex == words[NrOfCloud].length()) {
						bind[NrOfCloud] = true;
						sd[(level - 1) * 4 + 1].play();
						this.setStage(2);
						NrOfCloud = 0;
						wordindex = 1;
						check = false;

						break;
					}
				} else {
					for (int a = 4; a < 7; a++) {
						char[] list = new char[words[a].length()];
						list = this.changeStringToChar(words[a]);
						if (list[0] == input) {
							NrOfCloud = a;
							check = true;
						}
					}
				}
				break;
			case 2:
				if (NrOfCloud > 0) {
					char[] lists = new char[words[NrOfCloud].length()];
					lists = this.changeStringToChar(words[NrOfCloud]);
					if (wordindex < words[NrOfCloud].length()) {
						if (lists[wordindex] == input) {
							{
								wordindex++;
							}
						}
					}
					if (wordindex == words[NrOfCloud].length()) {
						bind[NrOfCloud] = true;
						sd[(level - 1) * 4 + 2].play();
						this.setStage(3);
						NrOfCloud = 0;
						wordindex = 1;
						check = false;

						break;
					}
				} else {
					for (int a = 1; a < 4; a++) {
						char[] list = new char[words[a].length()];
						list = this.changeStringToChar(words[a]);
						if (list[0] == input) {
							NrOfCloud = a;
							check = true;
						}
					}
				}
				break;
			case 3:
				char[] lists = new char[words[0].length()];
				lists = this.changeStringToChar(words[0]);
				if (wordindex < words[0].length()) {

					if (lists[wordindex] == input) {
						wordindex++;
						check = true;
					}
				}
				if (wordindex == words[NrOfCloud].length()) {
					bind[NrOfCloud] = true;
					this.setStage(0);
					this.setFrogPosition(level, 200 * level + 275);
					if (this.level < 4) {
						this.setLevel(this.level + 1);
						sd[(level - 1) * 4 + 3].play();

					} else {
						winAndCheckTime();
					}
					if (level == 4) {
						speed++;
					}

					this.createWord();
					wordindex = 1;
					check = false;
					for (int j = 0; j < 10; j++) {
						bind[j] = false;
					}
					break;
				}
				break;
			}
			this.setInput('0');
		}
	}
	
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public String[] getWords(){
		return words;
	}

	public void setMainApp(Main mainApp) {
	        this.mainApp = mainApp;
	    }
	 
    public void setPause(int pause){
    		this.Pause = pause; 
   }
   
	public void setStart(boolean start){
			this.Start = start;
	}
	
	
	public void setUser(User users){
			this.user = users;
	}
	
	public int getStage() {
			return stage;
	}

	public void setStage(int stage) {
			this.stage = stage;
	}

	public int getLevel() {
			return level;
	}

	public void setLevel(int level) {
			this.level = level;
	}
	
    public char getInput() {
    		return input;
    }

	public void setInput(char keyInput) {
			this.input = keyInput;
	}
	
	public char[] changeStringToChar(String a){
		
		char[] list1 = new char[a.length()];
		list1 = a.toCharArray();
		return list1;
    }
}