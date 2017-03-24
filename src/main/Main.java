package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import entity.User;
import control.PersonCenterController;
import control.RankingDialogController;
import control.LogOnDialogController;
import control.SignUpDialogController;

public class Main extends Application{
	
	//¡¡Define the primaryStage
	private Stage primaryStage;
	
	//  Define the Users Log on the game
	private User users;
	
	//  Define the RootLayout
    private BorderPane rootLayout;
    
    //  Define the URL to get the background music
    private static final String SOURCE = Main.class.getResource("BGM.wav").toExternalForm();
    
    //	Create the sound as BGM with MediaPlayer Class
    public MediaPlayer sound = new MediaPlayer(new Media(SOURCE));
    
	/**
     * Constructor
     */
	public Main(){
		
	}
	
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from FXML file.
            FXMLLoader loader = new FXMLLoader();
            String pathToFxml = "src/control/RootLayout.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);   
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Shows the logOn dialog inside the root layout.
     */
    public void showLogOnDialog() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            String pathToFxml = "src/control/LogOn.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);
            AnchorPane logOnDialog = (AnchorPane) loader.load();          

            // Set person overview into the center of root layout.
            rootLayout.setCenter(logOnDialog);     
            
            // Give the controller access to the main APP.
            LogOnDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(primaryStage);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to sign up if the user clicks SignUp button
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showSignUpDialog() {
        try {
            // Load the FXML file and create a new stage for the SignUp dialog.
            FXMLLoader loader = new FXMLLoader();
            String pathToFxml = "src/control/SignUp.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sign Up");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            SignUpDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Opens a dialog to show person center after logging on
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonCenter(User user) {
        try {

            users = user;
            // Load the FXML file and create a new stage for the Pop up dialog.
            FXMLLoader loader = new FXMLLoader();
            String pathToFxml = "src/control/PersonCenter.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Person Center");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonCenterController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.showPersonDetails(user);
            controller.setMainApp(this);
           
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Opens a dialog to show ranking
     */
    public boolean showRanking(){
    	 try {

             // Load the FXML file and create a new stage for the Pop up dialog.
             FXMLLoader loader = new FXMLLoader();
             String pathToFxml = "src/control/Ranking.fxml";
             URL fxmlUrl = new File(pathToFxml).toURI().toURL();
             loader.setLocation(fxmlUrl);
             AnchorPane page = (AnchorPane) loader.load();

             // Create the dialog Stage.
             Stage dialogStage = new Stage();
             dialogStage.setTitle("Person Center");
             dialogStage.initModality(Modality.WINDOW_MODAL);
             dialogStage.initOwner(primaryStage);
             Scene scene = new Scene(page);
             dialogStage.setScene(scene);

             // Set the person into the controller.
             RankingDialogController controller = loader.getController();
             controller.setDialogStage(dialogStage);
             controller.showAllUserInRanking();
            
             // Show the dialog and wait until the user closes it
             dialogStage.showAndWait();
             

             return true;
         } catch (IOException e) {
             e.printStackTrace();
             return false;
         }
    }
   
    
    /*
     * To send the current User to other panel.
     */
    public User getUser(){
    	return users;
    }
    

	
	public static void main(String[] args) {
		 launch(args);

	}

	@Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Set the application title.
        this.primaryStage.setTitle("Frog");
        
        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:Sources/Frog/FROG1.png"));

        initRootLayout();
        
        // Show the log on dialog.
        showLogOnDialog();
        
        // Make the background music none stop.
        sound.setCycleCount(-1);
        
        // Start the background music.
        sound.play();
                     
    }
}
