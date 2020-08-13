package strategies;

import java.util.ArrayList;

import core.Cell;
import core.Puzzle;
import core.Region;

public class HiddenSingle extends Strategy{
	
	private static HiddenSingle instance = null;

	private HiddenSingle() {
		super(true);
	}

	@Override
	public boolean work(Puzzle puzzle) {
		boolean changed = false;
      //  for (int i = 0; i < 9; i++) {
            for (Region r: puzzle.getRegions()) {
                for (int n = 1; n <= 9; n++)  {
                    ArrayList <Cell> cells = r.getCellsWithCandidate(n);
                    if (cells.size() == 1)
                    {
                    	Cell cell = cells.get(0);
                        cell.setValue(n);
                        System.out.println("Hidden single, " + cell + " value: " + cell.getValue());
                        changed = true;
                    }
                }
            }
       // }
        return changed;
	}
	
	public static HiddenSingle getInstance() {
		if (instance == null) instance = new HiddenSingle();
		return instance;
	}

}
