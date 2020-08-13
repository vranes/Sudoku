package strategies;

import java.util.ArrayList;

import core.Cell;
import core.Puzzle;

public class NakedSingle extends Strategy {
	
	public static NakedSingle instance = null;
	
	private NakedSingle() {
		super(true);
	}

	public boolean work (Puzzle puzzle) {

            boolean changed = false;
           
            for (int x = 0; x < 9; x++)
            {
                for (int y = 0; y < 9; y++)
                {
                	 Cell cell = puzzle.getCell(x, y);
                     if (cell.getValue() == 0)
                     {
                         ArrayList <Integer> candidates = cell.getCandidates();
                         if (candidates.size() == 1)
                         {
                             cell.setValue(candidates.get(0));
                            // Puzzle.LogAction("Naked single", new Cell[] { cell }, a);
                             System.out.println("Naked single, " + cell + " value: " + cell.getValue());
                             changed = true;
                         }
                     }
                }
            }
                   
           
            return changed;
	}
	
	public static NakedSingle getInstance() {
		if (instance == null) instance = new NakedSingle();
		return instance;
	}
}
