package strategies;

import core.Puzzle;

public class NakedQuadruple extends Strategy{
	
	private static NakedQuadruple instance = null;
	private NakedTuplesExposer exposer = NakedTuplesExposer.getInstance();

	private NakedQuadruple() {
		super(false);
	}

	@Override
	public boolean work(Puzzle puzzle) {
		 for (int i = 0; i < 9; i++)
         {
             if (exposer.findNakedTuples(puzzle, puzzle.getBlock(i), 4)
                 || exposer.findNakedTuples(puzzle, puzzle.getRow(i), 4)
                 || exposer.findNakedTuples(puzzle, puzzle.getColumn(i), 4))
             {
                 return true;
             }
         }
         return false;
	}

	public static NakedQuadruple getInstance() {
		if (instance==null) instance = new NakedQuadruple();
		return instance;
	}
}
