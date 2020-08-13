package gui;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import core.Cell;
import core.Puzzle;
import core.Solver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class GridGenerator {
	
    private TextField[][] boardFields = new TextField[9][9];
    Puzzle puzzle = new Puzzle();
    Solver solver = new Solver(puzzle);
    AlertManager alertManager = AlertManager.getInstance();
    
    public void initializeGrid (GridPane grid) {
    	for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = createTextField(i,j);
                grid.addRow(i, textField);
            }
        }
    }


    // dodajte textfield u boardFields
    public void addboardFields(int pos1, int pos2, TextField textField) {
        boardFields[pos1][pos2] = textField;
    }

    // osigurava da svako polje sadrzi jedan karakter, i to broj 
    private void limitInput(TextField textField) {
    	int maxLength = 1;
        textField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (textField.getText().length() > maxLength) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);
            }
            try {
               
                if(Integer.parseInt(textField.getText()) == 0) throw new NumberFormatException();
            } catch (NumberFormatException | NullPointerException e) {
                textField.setText("");
            }
        });
    }

    //kreira polja za board
    public TextField createTextField(int i, int j) {
        TextField textField = new TextField();
        limitInput(textField);
        formatTextField (textField);
        addboardFields(i, j, textField);
        formatBoard(i, j, textField);
        return textField;
    }

    //formatira polja
    public void formatTextField(TextField textField) {
        textField.setMaxSize(25, 10);
        textField.setMinWidth(30);
        textField.setAlignment(Pos.CENTER);
        textField.setPadding(new Insets(5.5, 15, 5, 5));
        textField.getStyleClass().add("textField");
    }
    
    //formatira tablu
    public void formatBoard(int i, int j, TextField textField) {
    	i++;
    	j++;
        if (j % 3 == 0 && j < 9) {
            GridPane.setMargin(textField, new Insets(0, 10, 0, 0));
        }
        if (i % 3 == 0 && i < 9) {
            GridPane.setMargin(textField, new Insets(0, 0, 10, 0));
        }
    }

    //resetuje view i model table
    public void clearBoard() {
        for (TextField[] textRow : boardFields) {
            for (TextField textField : textRow) {
                textField.setText("");
                enableTextField(textField);
                puzzle.clearBoard();
            }
        }
    }
    
    public void updateCell(Cell cell) {
    	if (cell.getValue() == 0)     	
    		(boardFields[cell.row][cell.column]).setText("");

    	else (boardFields[cell.row][cell.column]).setText(cell.getValue().toString());
    }
    
    
    private void tryBacktracking () {
    	if (solver.tryBacktracking()) {
    		alertManager.showSuccessAlert();
    		return;
    	}
    	alertManager.showBacktrackingFailedError();
 
    }

    private boolean inputCheck() {
        int inputs;
        Set<String> textSet = new HashSet<>(); // skup za tekstualne unose

        for (int i = 0; i < 9; i++) {
            inputs = 0;
            for (int j = 0; j < 9; j++) {
                if (!boardFields[i][j].getText().equals("")) {
                    inputs++;
                    textSet.add(boardFields[i][j].getText());
                }
            }
            if (textSet.size() != inputs) { // ako je vise unosa nego elemenata u skupu znaci da se neki broj ponavljao
                return false;
            }
            textSet.clear();
        }

        for (int i = 0; i < 9; i++) {
            inputs = 0;
            for (int j = 0; j < 9; j++) {
                if (!boardFields[j][i].getText().equals("")) {
                    inputs++;
                    textSet.add(boardFields[j][i].getText());
                }
            }
            if (textSet.size() != inputs) {
                return false;
            }
            textSet.clear();
        }

        for (int i = 0; i < 9; i += 3) { 			// proverava se i za redove, kolone i kutije istom logikom
            for (int j = 0; j < 9; j += 3) {
                inputs = 0;
                for (int k = i; k < (i + 3); k++) {
                    for (int l = j; l < (j + 3); l++) {
                        if (!boardFields[k][l].getText().equals("")) {
                            inputs++;
                            textSet.add(boardFields[k][l].getText());
                        }
                    }
                }
                if (textSet.size() != inputs) {
                    return false;
                }
                textSet.clear();
            }
        }
        return true;
    }

   
    public void solveSudoku() {	
    	
        if (! initializePuzzle()) {		// ako je pogresan unos
        	alertManager.showInputWarning();
        	return;
        }
        
        if (!solver.solve()) { //ako ne uspeju strategije ponuditi opciju za bektreking
        	if (alertManager.showBacktrackingOption()) tryBacktracking();
        	return;
        }
        
        alertManager.showSuccessAlert();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardFields[i][j].setText(puzzle.getCell(i, j).getValue().toString());
            }
        }
        
    } 

    private void disableTextField(TextField textField) {
        textField.setEditable(false);
    }

    private void enableTextField(TextField textField) {
        textField.setEditable(true);
    }
    
    public boolean initializePuzzle() {
    	boolean empty = true;
    	for (int i = 0; i<9; i++) {
    		for (int j = 0; j<9; j++) {
    			if (! boardFields[i][j].getText().isEmpty()) empty = false;
    		}
    	}
    	if (empty || ! inputCheck()) return false;
    	
    	puzzle.initialize(boardFields);
    	return true;
    	
    	//boardFields[0][0].setEditable(false); TODO
    }

}
