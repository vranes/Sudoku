package strategies;

import core.Puzzle;

public class JellyFish extends Strategy{
	
	private static JellyFish instance = null;

	public JellyFish() {
		super(false);
	}

	@Override
	public boolean work(Puzzle puzzle) {
		return FishExposer.findFish(puzzle, 4);
	}
	
	public static JellyFish getInstance() {
		if (instance == null) instance = new JellyFish();
		return instance;
	}
}
