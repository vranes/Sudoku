package gui;

import core.Cell;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainFrame {
		
		private static MainFrame instance;
	    private Stage primaryStage;
	    private GridGenerator generator;

	    private MainFrame() {
	        generator = new GridGenerator();
	    }
	    
	    public GridGenerator getGenerator() {
	    	return generator;
	    }

	    public Scene getScene() throws NoSuchMethodException {
	        primaryStage.resizableProperty().setValue(false);
	        BorderPane root = new BorderPane();
	        root.setCenter(mainContainer());
	        root.setBottom(buttons());
	        Scene mainScene = new Scene(root, 550, 550, Color.TRANSPARENT);
	        return mainScene;
	    }


	    private HBox mainContainer() {
	        HBox mainContainer = new HBox();
	        HBox.setHgrow(mainContainer, Priority.ALWAYS);
	        mainContainer.setAlignment(Pos.CENTER);

	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(4);
	        grid.setVgap(4);
	        grid.setPadding(new Insets(0, 5, 0, 5));
	        
	        generator.initializeGrid(grid);
	        mainContainer.getChildren().add(grid);
	        return mainContainer;
	    }

	    private HBox buttons() throws NoSuchMethodException {
	        HBox buttons = new HBox();
	        HBox.setHgrow(buttons, Priority.ALWAYS);
	        buttons.setAlignment(Pos.CENTER);
	        buttons.setPadding(new Insets(10, 10, 10, 10));
	        buttons.setSpacing(25);

	        Button clear = new Button("Clear Board");
	        clear.setOnAction((ae) -> generator.clearBoard());
	        clear.setPrefHeight(35);

	        Button solve = new Button("Solve");
	        solve.setPrefHeight(35);
	        solve.setOnAction((ae) -> generator.solveSudoku());
	     

	        Button dancing = new Button("Dancing Links");
	        dancing.setPrefHeight(35);
	        dancing.setMinWidth(100);
	        
	        
	        
	        buttons.getChildren().add(solve);
	        //buttons.getChildren().add(dancing);
	        buttons.getChildren().add(clear);
	        return buttons;
	    }
	    
	    public void setStage (Stage primaryStage) {
	    	this.primaryStage = primaryStage;
	    }
	    
	    public static MainFrame getInstance() {
	    	if (instance == null) instance = new MainFrame();
	    	return instance;
	    }
	    
	    public void updateCell (Cell cell) {
	    	generator.updateCell(cell);
	    }
}