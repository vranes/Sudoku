package core;

import java.util.ArrayList;

public class Region {
	private ArrayList <Cell> cells = new ArrayList<>();
	
	public Region(ArrayList<Cell> cells) {
		this.cells = cells;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}
	
	public boolean containsNumber(int n) {
		for (Cell c: cells) {
			if (c.getValue() == n) return true;
		}
		return false;
	}
	
	public ArrayList<Cell> getCellsWithCandidate (int n){
		ArrayList <Cell> result = new ArrayList<>();
		for (Cell cell: cells) {
			if (cell.getCandidates().contains(n)) result.add(cell);
		}
		return result;
	}
	
}
