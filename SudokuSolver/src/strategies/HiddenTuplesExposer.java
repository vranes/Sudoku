package strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import core.Cell;
import core.Puzzle;
import core.Region;

public class HiddenTuplesExposer { 
	
	/*
	  Nalazi 2/3/4 celije koje imaju 2/3/4 kandidata 
	  koji se ne pojavljuju nigde drugo u regionu (kuci)
	  i iz njih uklanja ostale kandidate.
	  Ne moraju biti svi kandidati u svim celijama ali
	  moraju svi biti u nekoj od njih.
	  
	 */
	
	private static HiddenTuplesExposer instance = null;
	private Puzzle puzzle;
	private Region region;
	private int amount;
	
	private HiddenTuplesExposer() {}
	
	 public boolean findHiddenTuples(Puzzle puzzle, Region region, int amount)
     {
		 
		 ArrayList<Cell> cellsWithCandidates = new ArrayList<>();
		 for (Cell cell: region.getCells()) {
			 if (cell.getCandidates().size() > 0) cellsWithCandidates.add(cell);
		 }
         if (cellsWithCandidates.size() == amount)
        	 return false; 
    
         //ako je celija sa kandidatima samo onoliko koliko ih trazimo 
         //jer u tom slucaju nema kandidata za uklanjanje 
        
         this.puzzle = puzzle;
 		 this.region = region;
 		 this.amount = amount;
 		 ArrayList<Integer> candidates = new ArrayList<>();
 		 for (int i=0; i<amount; i++) candidates.add(0);
         return work(0, candidates);
     }
	 
	 private boolean work(int loop, ArrayList<Integer> candidates)
     {
		 
         if (loop == amount) //kada postoji onoliko kandidata u listi koliko trazimo
         {
             ArrayList<Cell> cells = new ArrayList<>();
             for (int candidate: candidates) {
            	 ArrayList<Cell> cellsWithCandidates = region.getCellsWithCandidate(candidate);
            	 for(Cell curr: cellsWithCandidates) {
            		 if (!cells.contains(curr)) cells.add(curr);
            	 }
             } //skup svih celija sa kandidatima iz liste
             
             ArrayList<Integer> cands = new ArrayList<Integer>();
             for (Cell cell: cells) {
            	 ArrayList<Integer> candsOfCell = cell.getCandidates();
            	 for (Integer candidate: candsOfCell) {
            		 if (!cands.contains(candidate)) cands.add(candidate);
            	 }
             } //skup svih kandidata od celija iz skupa iznad
             
             if (cells.size() != amount // nema onoliko celija koliko trazimo
                 || cands.size() == amount // ako je kandidata onoliko koliko trazimo tj nema sta da se uklanja
                 || !cands.containsAll(candidates)) //ako se trazeni kandidati ne pojavljuju svi u celjiama 
             {
                return false;  
             }
             TreeSet<Integer> inverseCands =  new TreeSet<>();
             inverseCands.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
             inverseCands.removeAll(candidates);
             if (puzzle.changeCandidates(cells, inverseCands, true)) {
            	 String str = "";
            	 if (amount == 2) str = "Pair";
            	 else if (amount == 3) str = "Triple";
            	 else if (amount == 4) str = "Quadruple";
                 System.out.println("Hidden " + str + " ," + cells  +  " - candidates: " + candidates);
                 return true;
             }
         }
         
         else {
        	 //rekurzivno isprobava sve kombinacije kandidata
        	 
        	 int temp = loop-1; //temp nam sluzi da dobijemo vrednost iz prethodnog loopa
        	 if (temp<0) temp = 0; //temp je 0 u loop 0 i 1 jer ce u 0 loopu na indeksu 0 da stoji 0 pa +1 = 1 a u sledecem 1 + 1 = 2
             for (int i = candidates.get(temp) + 1; i <= 9; i++) { //i krece od broja za 1 veceg od prethodnog, da ne bi uzeli isti broj
                 candidates.set(loop, i); //i je kandidat koji se dodaje u listu a loop je mesto u listi
                 if (work(loop + 1, candidates)) { //rekurzija
                     return true;
                 }
             }
         }
         return false;
     }
	 
	 public static HiddenTuplesExposer getInstance() {
		 if (instance==null) instance = new HiddenTuplesExposer();
		 return instance;
	 }
}
