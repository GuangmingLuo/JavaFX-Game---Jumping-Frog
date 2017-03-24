package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	
	private String name;
	private String password;
	private int maxGrade;
	private String minTime;
	private StringProperty Name;
	private StringProperty MaxGrade;
	
	public User(String name, String password){
		this.name = name;
		this.password = password;
		this.Name = new SimpleStringProperty(name);
		this.MaxGrade = new SimpleStringProperty(Integer.toString(0));
		
	}

	public User(String name, String password, int maxGrade, String minTime){
		this.name = name;
		this.password = password;
		this.setMaxGrade(maxGrade);
		this.setMinTime(minTime);
		this.Name = new SimpleStringProperty(name);
		this.MaxGrade = new SimpleStringProperty(Integer.toString(maxGrade));
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxGrade() {
		return maxGrade;
	}

	public void setMaxGrade(int maxGrade) {
		this.maxGrade = maxGrade;
	}

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public StringProperty NameProperty(){		
		return Name;
	}
	
	public StringProperty MaxGradeProperty(){		
		return MaxGrade;
	}
}
