package ui;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Layer {

	//	X position of the picture.
	private int x;
	
	//	Y position of the picture.
	private int y;
	
	//	The level of the game.
	private int level;
	
	//	The name of the picture.
	private String name;
	
	
	public Layer(int x, int y, int level, String name) {
		this.x = x;
		this.y = y;
		this.level = level;
		this.name = name;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}



	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	/*
	 *  This method is to help us get the different cloud and frog.
	 */
	public void draw(Graphics g){
		
		g.drawImage(new ImageIcon("Sources/" + this.name + "/" + this.name + level + ".png").getImage(), x, y,null);
		
	}
	

}
