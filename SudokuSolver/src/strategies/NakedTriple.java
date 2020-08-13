package strategies;

import core.Puzzle;

public class NakedTriple extends Strategy{
	
	private static NakedTriple instance = null;
	private NakedTuplesExposer exposer = NakedTuplesExposer.getInstance();


	private NakedTriple() {
		super(false);
	}

	@Override
	public boolean work(Puzzle puzzle) {
		 for (int i = 0; i < 9; i++)
         {
             if (exposer.findNakedTuples(puzzle, puzzle.getBlock(i), 3)
                 || exposer.findNakedTuples(puzzle, puzzle.getRow(i), 3)
                 || exposer.findNakedTuples(puzzle, puzzle.getColumn(i), 3))
             {
                 return true;
             }
         }
         return false;
	}
	
	public static NakedTriple getInstance() {
		if (instance == null) instance = new NakedTriple();
		return instance;
	}

}
