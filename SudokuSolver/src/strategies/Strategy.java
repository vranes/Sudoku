package strategies;

import core.Puzzle;

public abstract class Strategy {
	public boolean repeat;

	public Strategy(boolean repeat) {
		this.repeat = repeat;
	}
	
	public abstract boolean work(Puzzle puzzle);
	
}
