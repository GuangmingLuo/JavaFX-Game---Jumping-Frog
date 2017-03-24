package control;

import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import persistence.UserMapper;

public class RankingDialogController {
	
	    @FXML
	    private TableView<User> personTable;
	    @FXML
	    private TableColumn<User, String> nameColumn;
	    @FXML
	    private TableColumn<User, String> MaxGradeColumn;
	    
	    
	    private Stage dialogStage;

	    private UserMapper um = UserMapper.UNIQUEINSTANCE;
	    
	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public RankingDialogController(){
	    	
	    }
	    
	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
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
	    
	    
	    public void showAllUserInRanking(){
	    	personTable.setItems(um.getAllUsersByGradeRanking());
	    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
	    	MaxGradeColumn.setCellValueFactory(cellData -> cellData.getValue().MaxGradeProperty());
	    }
	    
	    /**
	     * Called when the user clicks cancel.
	     */
	    @FXML
	    private void handleClose(){
	    	dialogStage.close();
	    	
	    }
}
