package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javafx.scene.control.TextField;

public class Puzzle {
	private ArrayList <Region> rows = new ArrayList<>();
	private ArrayList <Region> columns = new ArrayList<>();
	private ArrayList <Region> blocks = new ArrayList<>();
	private ArrayList <Region> regions = new ArrayList<>();
	private Cell[][] board;
	
	
	public void initialize(TextField[][] textfields) {
		board = new Cell [9][9];
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				Cell newCell = new Cell(textfields[i][j], i ,j, this);
				board[i][j] = newCell;
			}
		}
		
		for (int i = 0; i < 9; i++) {
            ArrayList <Cell> cells = new ArrayList<>();
            Region newRegion;
            int c;
            
            for (c = 0; c < 9; c++)  {
                cells.add(c, this.board[i][c]);
            }
            newRegion = new Region(cells);
            rows.add(i, newRegion);
            regions.add(newRegion);

            cells = new ArrayList<>();
            for (c = 0; c < 9; c++) {
                cells.add(c, this.board[c][i]);
            }
            newRegion = new Region(cells);
            regions.add(newRegion);
            columns.add(i, newRegion);

            cells = new ArrayList<>();
            c = 0;
            int ybox = i % 3 * 3,
                xbox = i / 3 * 3;
            for (int x = xbox; x < xbox + 3; x++)  {
                for (int y = ybox; y < ybox + 3; y++)  {
                    cells.add(c++, this.board[x][y]);
                }
            }
            newRegion = new Region(cells);
            regions.add(newRegion);
            blocks.add(i, newRegion);
        }
		refreshCandidates();
	}
	
	public Cell getCell(int x, int y) {
		return board[x][y];
	}
	
	 public void refreshCandidates() {
         for (int x = 0; x < 9; x++) {
             for (int y = 0; y < 9; y++)   {
                 Cell cell = board[x][y];
                 int block = (x/3)*3 + y/3;
                 cell.clearCandidates();
                 for(int n=1; n<=9; n++) {
                	 if (cell.isEditable() && cell.getValue() == 0 && !rows.get(x).containsNumber(n) && !columns.get(y).containsNumber(n) && !blocks.get(block).containsNumber(n))
                		 cell.addCandidate(n);
                 }
             }
         }
     }
	 
	 public boolean changeCandidates(ArrayList<Cell> intersected, TreeSet <Integer> candidates, boolean remove)  {
         boolean changed = false;
         for (Cell cell: intersected)  {
             for (Integer candidate: candidates)   {
            	 
            	 if (remove && cell.removeCandidate(candidate)) {
            		 changed = true;
            	 }
            	 
            	 if (!remove) {
            		 cell.getCandidates().add(candidate);
            		 changed = true;
            	 }
             }
         }
         return changed;
     }
	 
	 public ArrayList<Region> getRegions() {
		 return regions;
	 }
	 
	 public Region getRow (int n) {
		 return rows.get(n);
	 }
	 
	 public Region getColumn (int n) {
		 return columns.get(n);
	 }
	 
	 public Region getBlock (int n) {
		 return blocks.get(n);
	 }
	 
	 public void clearBoard() {
		 rows.clear();
		 columns.clear();
		 blocks.clear();
		 regions.clear();
		 board = new Cell[9][9];
	 }
	 
	 public Cell[][] getBoard() {
		 return board;
	 }
	 
	 public boolean isValid() {
	        int values;
	        Set<Integer> intSet = new HashSet<>(); // skup za vrednosti u celijama

	        for (int i = 0; i < 9; i++) {
	            values = 0;
	            for (int j = 0; j < 9; j++) {
	                if (! (board[i][j].getValue() == 0)) {
	                    values++;
	                    intSet.add(board[i][j].getValue());
	                }
	            }
	            if (intSet.size() != values) { // ako je vise vrednosti nego elemenata u skupu znaci da se neki broj ponavljao
	                return false;
	            }
	            intSet.clear();
	        }

	        for (int i = 0; i < 9; i++) {
	            values = 0;
	            for (int j = 0; j < 9; j++) {
	                if (! (board[j][i].getValue() == 0)) {
	                    values++;
	                    intSet.add(board[j][i].getValue());
	                }
	            }
	            if (intSet.size() != values) {
	                return false;
	            }
	            intSet.clear();
	        }

	        for (int i = 0; i < 9; i += 3) { 			// proverava se i za redove, kolone i kutije istom logikom
	            for (int j = 0; j < 9; j += 3) {
	                values = 0;
	                for (int k = i; k < (i + 3); k++) {
	                    for (int l = j; l < (j + 3); l++) {
	                        if (! (board[k][l].getValue() == 0)) {
	                            values++;
	                            intSet.add(board[k][l].getValue());
	                        }
	                    }
	                }
	                if (intSet.size() != values) {
	                    return false;
	                }
	                intSet.clear();
	            }
	        }
	        return true;
	    }

	public ArrayList<Region> getRows() {
		return rows;
	}

	public ArrayList<Region> getColumns() {
		return columns;
	}

	public ArrayList<Region> getBlocks() {
		return blocks;
	}

	 
}