package strategies;

import core.Puzzle;

public class HiddenTriple extends Strategy{
	
	private static HiddenTriple instance = null;
	private HiddenTuplesExposer exposer = HiddenTuplesExposer.getInstance();

	private HiddenTriple() {
		super(false);
	}

	@Override
	public boolean work(Puzzle puzzle) {
		for (int i = 0; i < 9; i++)
        {
            if (exposer.findHiddenTuples(puzzle, puzzle.getBlock(i), 3)
                || exposer.findHiddenTuples(puzzle, puzzle.getRow(i), 3)
                || exposer.findHiddenTuples(puzzle, puzzle.getColumn(i), 3))
            {
                return true;
            }
        }
        return false;
	}
	
	public static HiddenTriple getInstance() {
		if (instance == null) instance = new HiddenTriple();
		return instance;
	}

}
