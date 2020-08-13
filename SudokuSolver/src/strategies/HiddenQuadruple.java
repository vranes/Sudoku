package strategies;

import core.Puzzle;

public class HiddenQuadruple extends Strategy{
	
	public HiddenQuadruple() {
		super(false);
	}

	private static HiddenQuadruple instance = null;
	private HiddenTuplesExposer exposer = HiddenTuplesExposer.getInstance();
	
	@Override
	public boolean work(Puzzle puzzle) {
		for (int i = 0; i < 9; i++)
        {
            if (exposer.findHiddenTuples(puzzle, puzzle.getBlock(i), 4)
                || exposer.findHiddenTuples(puzzle, puzzle.getRow(i), 4)
                || exposer.findHiddenTuples(puzzle, puzzle.getColumn(i), 4))
            {
                return true;
            }
        }
        return false;
	}
	
	public static HiddenQuadruple getInstance() {
		if (instance==null) instance = new HiddenQuadruple();
		return instance;
	}
}
