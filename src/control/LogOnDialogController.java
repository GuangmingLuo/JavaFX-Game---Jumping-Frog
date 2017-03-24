package control;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

import java.awt.Frame;
import java.util.List;

import javax.swing.JOptionPane;

import entity.User;
import persistence.UserMapper;

public class LogOnDialogController {
	
	//  To write your user name.
	@FXML
    private TextField nameField;
	
	//	To write your password.
    @FXML
    private PasswordField passwordField;

    //	Define the user log on the game.
    private User user;
    
    //  Define the mainApp object.
    private Main mainApp;
    
    //	Define the password of this user.
    private String password;
    
    //  Define the current dialogStage.
    private Stage dialogStage;
    
    //  Define the Button and check if it is clicked.
    private boolean okClicked = false;
    
    //  Create a UserMapper to storage info in the database.
    UserMapper um = UserMapper.UNIQUEINSTANCE;
    
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LogOnDialogController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    
    /**
     * Called when the user clicks logOn.
     */
    @FXML
    private void handleLogOn() {
    	List<String> names = um.getUserNames();
    	boolean ok = isInputValid();
    	if(ok){
    		if(names.contains(nameField.getText())){
    			//JOptionPane.showMessageDialog(new Frame(), "This user name does not exist!");
        		this.user = um.getUserByName(nameField.getText()); 
        		password = user.getPassword();
        		if(password.equals(passwordField.getText())){
        			okClicked = true;
        			mainApp.showPersonCenter(this.user);  
        			dialogStage.close();
        		}else{
        			JOptionPane.showMessageDialog(new Frame(), "Your password is not correct!");
        		}
    		}else {
                // Show the error message.
            	JOptionPane.showMessageDialog(new Frame(), "This user name is not exist!");
                 }
       	}
    }
    
    /**
     * Called when the user clicks sign up.
     */
    @FXML
    private void handleSignUp() {           
    	 mainApp.showSignUpDialog();     
        
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid first name or password!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
        	JOptionPane.showMessageDialog(new Frame(), errorMessage);

            return false;
             }
    }
}