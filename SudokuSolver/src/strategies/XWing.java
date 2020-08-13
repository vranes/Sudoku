package strategies;

import core.Puzzle;

public class XWing extends Strategy{
	private static XWing instance = null;

	public XWing() {
		super(false);
	}

	@Override
	public boolean work(Puzzle puzzle) {
		return FishExposer.findFish(puzzle, 2);
	}
	
	public static XWing getInstance() {
		if (instance == null) instance = new XWing();
		return instance;
	}
}
