package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertManager {
	private static AlertManager instance = null;
	
	private AlertManager() {
		
	}
	
	public void showInputWarning() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Puzzle");
        alert.setContentText("Please check to ensure that the input is a valid sudoku puzzle.");
        alert.showAndWait();
       
    }
    
    public boolean showBacktrackingOption() {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Unable to solve using strategies, try backtracking instead?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Backtracking?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            return true;
        }
        return false;

    }
    
    public void showBacktrackingFailedError() {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Backtracking unsuccessful");
    	alert.setContentText("Backtracking technique failed to succeed");
   	 	alert.showAndWait();
    }
    
    public void showSuccessAlert() {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setContentText("The puzzle is successfully solved !");
        alert.showAndWait();
    }
    
    public static AlertManager getInstance() {
    	if (instance==null) instance = new AlertManager();
    	return instance;
    }
}
