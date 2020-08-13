package core;

import java.util.ArrayList;
import java.util.TreeSet;

import gui.MainFrame;
import javafx.scene.control.TextField;

public class Cell implements Comparable{
	private Integer value = 0;
	private TextField textField;
	private boolean editable;
	public int row;
	public int column;
	public int block;
	private ArrayList <Integer> candidates = new ArrayList<>();
	private Puzzle puzzle;
	
	public Cell() {
		
	}
	
	public Cell (TextField ts, int x, int y, Puzzle puzzle) {
		textField = ts;
		row = x;
		column = y;
		block = (x/3)*3 + y/3;

		this.puzzle = puzzle;
		setEditable();
		updateValue();
	}
	
	public void updateValue() {
		if (textField.getText().isEmpty()) value = 0;
		else value = Integer.parseInt(textField.getText());
	}
	
	public Integer getValue () {
		return value;
	}
	
	public void setValue(Integer v) {
		value = v;
		MainFrame.getInstance().updateCell(this);
		
		if (v == 0) return; //mozda razdvojiti ovu metodu za bektreking
		
		candidates.clear();
		TreeSet<Integer> set = new TreeSet<>();
		set.add(v);
		puzzle.changeCandidates(this.getVisibleCells(), set, true);
	}
	
	public void addCandidate(Integer c) {
		if (candidates.contains(c)) return;
		candidates.add(c);
	}
	
	public void clearCandidates() {
		candidates.clear();
	}
	
	public boolean removeCandidate (Integer c) {
		if (! candidates.contains(c)) return false;
		candidates.remove(c);
		return true;
	}
	
	public void setEditable() {
		if (textField.getText().isEmpty()) editable = true;
		else editable = false;
	}
	
	public ArrayList<Integer> getCandidates() {
		return candidates;
	}
	
	public void setCandidates(ArrayList<Integer> newCandidates) {
		this.candidates = newCandidates;
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public ArrayList <Cell> getVisibleCells() {
		ArrayList<Cell> visible = new ArrayList<>();
		for (Cell cell: puzzle.getRow(row).getCells()) {
			if (! visible.contains(cell) && cell != this) visible.add(cell);
		}
		for (Cell cell: puzzle.getColumn(column).getCells()) {
			if (! visible.contains(cell) && cell != this) visible.add(cell);
		}
		for (Cell cell: puzzle.getBlock(block).getCells()) {
			if (! visible.contains(cell) && cell != this) visible.add(cell);
		}
		
		return visible;
	}

	@Override
	public int compareTo(Object o) {
		if (((Cell)o).row == this.row && ((Cell)o).column == this.column)
			return 0;
		
		else return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	public String toString() {
		return "row: " + row + ", column: " + column;
	}
	
}
