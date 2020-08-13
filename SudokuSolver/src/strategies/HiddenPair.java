package strategies;

import core.Puzzle;

public class HiddenPair extends Strategy{
	
	private static HiddenPair instance = null;
	private HiddenTuplesExposer exposer = HiddenTuplesExposer.getInstance();

	private HiddenPair() {
		super(false);
	}

	@Override
	public boolean work(Puzzle puzzle) {
            for (int i = 0; i < 9; i++)
            {
                if (exposer.findHiddenTuples(puzzle, puzzle.getBlock(i), 2)
                    || exposer.findHiddenTuples(puzzle, puzzle.getRow(i), 2)
                    || exposer.findHiddenTuples(puzzle, puzzle.getColumn(i), 2))
                {
                    return true;
                }
            }
            return false;
	}

	public static HiddenPair getInstance() {
		if (instance == null) instance = new HiddenPair();
		return instance;
	}
}
