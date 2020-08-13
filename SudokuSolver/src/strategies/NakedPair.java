package strategies;

import core.Puzzle;


public class NakedPair extends Strategy{

	private static NakedPair instance = null;
	private NakedTuplesExposer exposer = NakedTuplesExposer.getInstance();

	private NakedPair() {
		super(false);
	}

	@Override
	public boolean work(Puzzle puzzle) {
		
		for (int i = 0; i < 9; i++)
        {
            if (exposer.findNakedTuples(puzzle, puzzle.getBlock(i), 2)
                || exposer.findNakedTuples(puzzle, puzzle.getRow(i), 2)
                || exposer.findNakedTuples(puzzle, puzzle.getColumn(i), 2))
            {
                return true;
            }
        }
        return false;
	}
	
	public static NakedPair getInstance() {
		if (instance == null) instance = new NakedPair();
		return instance;
	}

}
