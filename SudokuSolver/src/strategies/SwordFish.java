package strategies;

import core.Puzzle;

public class SwordFish extends Strategy{
	private static SwordFish instance = null;

	public SwordFish() {
		super(false);
	}

	@Override
	public boolean work(Puzzle puzzle) {
		return FishExposer.findFish(puzzle, 3);
	}
	
	public static SwordFish getInstance() {
		if (instance == null) instance = new SwordFish();
		return instance;
	}
}
