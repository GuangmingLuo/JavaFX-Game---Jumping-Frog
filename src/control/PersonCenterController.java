package control;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import ui.FrameGame;
import entity.User;

public class PersonCenterController {

	//	Show your user name.
    @FXML
    private TextField Name;
    
    //	Show your max grade.
    @FXML
    private TextField MaxGrade;
    
    //  Show your minTime in the game.
    @FXML
    private TextField MinTime;
    
    //	Create A frame to run the game.
    public FrameGame FG = new FrameGame();
    
    //  Define the current dialogStage.
    private Stage dialogStage;
    
    //  Define the Button and check if it is clicked.
    private boolean okClicked = false;
    
    //	Define the user log on the game.
    private User user;
    
    //  Define the mainApp object.
    private Main mainApp;
    
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonCenterController() {
    	
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Clear person details.
        showPersonDetails(null);

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
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    

    /*
     * Set user
     */
    public void setUser(User user){
    	this.user = user;
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        FG.setMainApp(this.mainApp);
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    public void showPersonDetails(User user) {
    	setUser(user);
    	FG.setUser(this.user);
        if (user != null) {
            // Fill the labels with info from the person object.
            Name.setText(user.getName());
            MaxGrade.setText(Integer.toString(user.getMaxGrade()));
            MinTime.setText(user.getMinTime());

        }else {
            // Person is null, remove all the text.
        	Name.setText("");
        	MaxGrade.setText("");
        	MinTime.setText("");

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
     * Called when the user clicks ShowRanking button.
     */
    @FXML
    private void handleShowRanking(){
    	mainApp.showRanking();
    }

    /**
     * Called when the user clicks startGame.
     * @throws InterruptedException 
     */
    @FXML
    private void handlestartGame(){
        FG.setVisible(true);
        FG.getFG(FG);
    }
}