package application;

import gui.MainFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	primaryStage.setTitle("Sudoku Solver");
    	primaryStage.getIcons().add(new Image("gui/sudoku.png"));
    	MainFrame.getInstance().setStage(primaryStage);
    	primaryStage.setScene(MainFrame.getInstance().getScene());
        primaryStage.sizeToScene();
        primaryStage.show();
    }

}