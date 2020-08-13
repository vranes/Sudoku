package strategies; 

import core.Puzzle;
import core.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import core.Region;

public class FishExposer {
	
	/*
	 * 2/3/4 reda/kolone (base sets) u kojima se odredjeni kandidat pojavljuje po 2/3/4 puta u celijama
	 * i postoje 2/3/4 kolone/reda (cover sets) koje sadrze te celije iz baze. 
	 * Brise pojavljivanje tih kandidata u celijiama cover seta koje nisu sadrzane u base setu.
	 */
	
	private static Puzzle puzzle;
	private static int amount;
	private FishExposer() {}
	
	 public static boolean findFish(Puzzle p, int n) {

		 puzzle = p;
		 amount = n;
         for (int candidate = 1; candidate <= 9; candidate++) {
             if (work(0, new int[amount], candidate)) return true;
             
         }
         return false;
     }
	 
	 private static boolean work(int loop, int[] indexes, int candidate) {
         if (loop == amount) {
        	 TreeSet<Integer> cands = new TreeSet<>();
        	 cands.add(candidate);
        	 
        	 // celije sa trazenim kandidatom iz redova sa prosledjenim indeksima
        	 ArrayList<ArrayList<Cell>> rowCells = new ArrayList<>();
        	 for (int i: indexes) {
        		 Region row = puzzle.getRow(i);
        		 rowCells.add(row.getCellsWithCandidate(candidate));
        	 }
        	 
        	 // celije sa trazenim kandidatom po kolonama sa prosledjenim indeksima
        	 ArrayList<ArrayList<Cell>> colCells = new ArrayList<>();
        	 for (int i: indexes) {
        		 Region column = puzzle.getColumn(i);
        		 colCells.add(column.getCellsWithCandidate(candidate));
        	 }
        	 
        	 ArrayList <Integer> rowLengths = new ArrayList<>();
        	 for (ArrayList<Cell> row: rowCells) {
        		 rowLengths.add(row.size());
        	 }
        	 
        	 ArrayList <Integer> colLengths = new ArrayList<>();
        	 for (ArrayList<Cell> column: colCells) {
        		 colLengths.add(column.size());
        	 }
             
             //skup svih indeksa kolona u kojima celije imaju trazene kandidate
             ArrayList<Integer> colIndexes = new ArrayList<>();
             for (ArrayList<Cell> row: rowCells) {
            	 for (Cell cell: row) {
            		 if(!colIndexes.contains(cell.column)) colIndexes.add(cell.column);
            	 }
             }
             
             if (Collections.max(rowLengths) == amount && Collections.min(rowLengths) > 0 && colIndexes.size() <= amount) {
            	 
            	 // celije sa kandidatima iz redova pretocene u niz 
            	 ArrayList<Cell> rows1D = new ArrayList<>();
            	 for (ArrayList<Cell> row: rowCells) {
                	 for (Cell cell: row) {
                		 if (! rows1D.contains(cell)) rows1D.add(cell);
                	 }
                 }
            	 
            	 // za svaku celiju sa kandidatima uzeta kolona i sve celije odatle, osim njih samih
            	 ArrayList<Cell> columnsOfRows = new ArrayList<>();
            	 for (Cell cell: rows1D) {
            		 ArrayList<Cell> temp = puzzle.getColumn(cell.column).getCells();
            		 columnsOfRows.removeAll(temp);
            		 columnsOfRows.addAll(temp);
            		 columnsOfRows.removeAll(rows1D);
            	 }
            	 
            	 if (puzzle.changeCandidates(columnsOfRows, cands, true)) {
            		 String str = "fish";
            		 if (amount == 2) str = "XWing";
            		 else if (amount == 3) str = "Swordfish";
            		 else if (amount == 4) str = "JellyFish";	 
                     System.out.println("Found " + str + ", row cells: " + rows1D + " candidates: " + cands);
                     return true;
                 }
             }
             
             //skup svih indeksa redova u kojima celije imaju trazene kandidate
             ArrayList<Integer> rowIndexes = new ArrayList<>();
             for (ArrayList<Cell> column: colCells) {
            	 for (Cell cell: column) {
            		 if(! rowIndexes.contains(cell.row)) rowIndexes.add(cell.row);
            	 }
             }
             
             if (Collections.max(colLengths) == amount && Collections.min(colLengths) > 0 && rowIndexes.size() <= amount) {

            	// celije sa kandidatima iz kolona pretocene u niz 
            	 ArrayList<Cell> cols1D = new ArrayList<>();
            	 for (ArrayList<Cell> col: colCells) {
                	 for (Cell cell: col) {
                		 if (! cols1D.contains(cell)) cols1D.add(cell);
                	 }
                 }
            	 
            	 // za svaku celiju sa kandidatima uzet red i sve celije odatle, osim njih samih
            	 ArrayList<Cell> rowsOfColumns = new ArrayList<>();
            	 for (Cell cell: cols1D) {
            		 ArrayList<Cell> temp = puzzle.getRow(cell.row).getCells();
            		 rowsOfColumns.removeAll(temp);
            		 rowsOfColumns.addAll(temp);
            		 rowsOfColumns.removeAll(cols1D);
            	 }
            	 
            	 if (puzzle.changeCandidates(rowsOfColumns, cands, true)) {
            		 String str = "Fish";
            		 if (amount == 2) str = "XWing";
            		 else if (amount == 3) str = "Swordfish";
            		 else if (amount == 4) str = "JellyFish";	
                     System.out.println(str + ", col cells: " + cols1D + " candidates: " + cands);
                     return true;
                 }
             }
         }
         else {
             for (int i = loop == 0 ? 0 : indexes[loop - 1] + 1; i < 9; i++) {
                 indexes[loop] = i;
                 if (work(loop + 1, indexes, candidate)) {
                     return true;
                 }
             }
         }
         return false;
    }
	 
}