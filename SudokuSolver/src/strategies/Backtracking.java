package strategies;

import core.Cell;
import core.Puzzle;
import gui.MainFrame;

public class Backtracking extends Strategy{
	
	private static Backtracking instance = null;

	private Backtracking() {
		super(false);
	}

	/* 
	 * ako je validna nakon postavljanja vrednosti trenutnog polja, 
	 * rekurzivno poziva istu metodu koja ce u narednom pozivu
	 * uzeti sledece nepopunjeno polje i uraditi isto,
	 * ukoliko nije validna resetovace polje na 0 i vratiti netacno,
	 * nastaviti sa isprobavanjem vrednosti tamo gde je stao
	 * 
	 * */
	@Override
	public boolean work(Puzzle puzzle) {

		    for (int i = 0; i<9 ; i++) {
		        for (int j = 0; j<9 ; j++) {
		        	Cell cell = puzzle.getBoard()[i][j];
		            if (cell.getValue() == 0) {
		                for (int k = 1; k <= 9; k++) {
		                    cell.setValue(k);
		                    if (puzzle.isValid() && work(puzzle)) { 
		                        return true; 
		                    }
		                    cell.setValue(0);
		                }
		                return false;
		            }
		        }
		    }
		    return true;
	}
	
	public static Backtracking getInstance() {
		if (instance == null) instance = new Backtracking();
		return instance;
	}

}
