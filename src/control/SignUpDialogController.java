package control;

import persistence.UserMapper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.Frame;

import javax.swing.JOptionPane;

import entity.User;

public class SignUpDialogController {
	
	//  SignUp your user name.
    @FXML
    private TextField NameField;
    
	//  SignUp your password.
    @FXML
    private TextField PasswordField1;
    
    //	To make sure your enter the correct password.
    @FXML
    private TextField PasswordField2;

    //  Define the current dialogStage.
    private Stage dialogStage;
    
    //  Define the Button and check if it is clicked.
    private boolean okClicked = false;
    
    //	Define the user log on the game.
    private User user;
    
    //  Create a UserMapper to storage info in the database.
    UserMapper um = UserMapper.UNIQUEINSTANCE;
    
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
    
    /*
     * Set user
     */
    public void setUser(User user){
    	this.user = user;
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
     * Called when the user clicks OK.
     */
    @FXML
    private void handleOk() {
    	
        if (isInputValid()) {
        	if(!um.getUserNames().contains(NameField.getText())){
        	  	try{
            		this.user = new User(NameField.getText(),PasswordField1.getText());
            	
            	}catch(Exception ex){
             }
        	}else{
        		// If the user name already exist,show the error message.
        		JOptionPane.showMessageDialog(new Frame(), "This user name is in use already, please change a new one !");
        	} 
            um.createUser(this.user);
            okClicked = true;
            dialogStage.close();
        }
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel(){
    	dialogStage.close();
    	
    }
    
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (NameField.getText() == null || NameField.getText().length() == 0) {
            errorMessage += "No valid name!\n"; 
        }
        if (PasswordField1.getText() == null || PasswordField1.getText().length() == 0) {
            errorMessage += "No valid password!\n"; 
        }
        if (PasswordField2.getText() == null || PasswordField2.getText().length() == 0) {
            errorMessage += "No valid password!\n"; 
        }
        if (! PasswordField1.getText().equals(PasswordField2.getText()) ) {
            errorMessage = "The two passwords are not the same, please rewrite them !\n"; 
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
