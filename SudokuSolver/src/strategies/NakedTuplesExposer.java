package strategies;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import core.Cell;
import core.Puzzle;
import core.Region;

public class NakedTuplesExposer {
	
	/*
	  Nalazi 2/3/4 celije koje sadrze ukupno samo 2/3/4 kandidata
	  i te kandidate uklanja iz ostalih celija tog regiona (kuce).
	  Ne moraju svi kandidati biti u svim celijama ali bitno je da u 
	  tim celijama nema drugih kanidata.
	*/
	
	private static NakedTuplesExposer instance = null;
	private Puzzle puzzle;
	private Region region;
	private int amount;
	
	private NakedTuplesExposer() {
	}
	
	public boolean findNakedTuples(Puzzle puzzle, Region region, int amount) {
		this.puzzle = puzzle;
		this.region = region;
		this.amount = amount;
		ArrayList<Cell> cells = new ArrayList<>();
		for (int i=0; i<amount; i++) cells.add(new Cell());
		return doNakedTuples(0, cells, new int[amount]);
	}
	
	private boolean doNakedTuples(int loop, ArrayList<Cell> cells, int[] indexes)  {
        if (loop == amount) {
        	
            TreeSet <Integer> combo = new TreeSet<>();
            for (Cell cell: cells)	combo.addAll(cell.getCandidates()); //svi kandidati u naciljanim celijama
            
            if (combo.size() == amount) { //ako je kandidata onoliko koliko polja trazimo
            	
            	ArrayList<Cell> intersected = new ArrayList<>();
            	for (int i: indexes) {
            		ArrayList<Cell> visible = region.getCells().get(i).getVisibleCells();
            		for (Cell cell: visible) {
            			if (!intersected.contains(cell)) intersected.add(cell);
            		}
            	} //set koji sadrzi sve celije vidljive nekoj od naciljanih celija (bez ponavljanja)
            	
            	for (int i: indexes) {
            		ArrayList<Cell> visible = region.getCells().get(i).getVisibleCells();
            		ArrayList<Cell> removal = new ArrayList<>();
            		for (Cell cell: intersected) {
            			if (!visible.contains(cell)) {
            				removal.add(cell);
            			}
            		}
            		intersected.removeAll(removal);
            		
            	} //intersekcija polja koja su vidljiva obema naciljanim celijama
            	
            	if(puzzle.changeCandidates(intersected, combo, true)) { //pokusaj da uklonis iz polja vidljivih naciljanim celijama zajednicke kandidate
            		 String str = "";
                	 if (amount == 2) str = "Pair";
                	 else if (amount == 3) str = "Triple";
                	 else if (amount == 4) str = "Quadruple";
                     System.out.println("Hidden " + str + " ," + cells +  " - candidates: " + combo);
            		return true;
            	}
            }
        }
        
        else
        {
        	int temp;
        	if (loop == 0) temp = 0;
        	else temp = indexes[loop - 1] + 1;
            for (int i = temp; i < 9; i++)
            {
                Cell c = region.getCells().get(i);
                if (c.getCandidates().size() == 0) continue;
                
                cells.set(loop, c);
                indexes[loop] = i;
                if (doNakedTuples(loop + 1, cells, indexes))
                    return true;  
            }
        }
        return false;
    }

	public static NakedTuplesExposer getInstance() {
		if (instance ==  null) instance = new NakedTuplesExposer();
		return instance;
	}
}
