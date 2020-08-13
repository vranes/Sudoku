package core;

import java.util.ArrayList;

import strategies.Backtracking;
import strategies.HiddenPair;
import strategies.HiddenQuadruple;
import strategies.HiddenSingle;
import strategies.HiddenTriple;
import strategies.JellyFish;
import strategies.NakedPair;
import strategies.NakedQuadruple;
import strategies.NakedSingle;
import strategies.NakedTriple;
import strategies.Strategy;
import strategies.SwordFish;
import strategies.XWing;

public class Solver {
	private Puzzle puzzle;
	private Strategy previousStrategy;
	private ArrayList <Strategy> strategies = new ArrayList<>();
	
	public Solver(Puzzle puzzle) {
        this.puzzle = puzzle;
        strategies.add(NakedSingle.getInstance());
        strategies.add(HiddenSingle.getInstance());
        strategies.add(NakedPair.getInstance());
        strategies.add(HiddenPair.getInstance());
        strategies.add(NakedTriple.getInstance());
        strategies.add(HiddenTriple.getInstance());
        strategies.add(NakedQuadruple.getInstance());
        strategies.add(HiddenQuadruple.getInstance());
        strategies.add(XWing.getInstance());
        strategies.add(SwordFish.getInstance());
        strategies.add(JellyFish.getInstance());
    }
	
	public boolean solve() {
        puzzle.refreshCandidates();
       // previousStrategy = null; //TODO
        boolean solved; // ako je ovo true sudoku je resen i breakujemo
        do {
            solved = true;

            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    Cell cell = puzzle.getCell(x, y);
                    if (cell.getValue() == 0)  {
                        solved = false;
                    }
                }
            }
            
            if (solved || ! tryStrategies()) {
                break;
            }

        } while (true);
        
        return solved;
    }
	
	private boolean tryStrategies()
    {
        for (Strategy s: strategies)
        {
        	//nadji sledecu strategiju za rad, preskoci prethodnu osim ako nije dobra za ponavljanje
            if ((s != previousStrategy || s.repeat) && s.work(puzzle))
            {
                previousStrategy = s; //ako je delovala ona postaje prev
                return true;
            }
        }
        if (previousStrategy == null)
        {
            return false; //ako nijedna nije delovala nema pomoci
        }
        else
        {
            return previousStrategy.work(puzzle); //ako nije delovala nijedna od ispitanih probamo proslu koju smo sad preskocili
        } 
        /*
		for (Strategy s: strategies) {
			if (s.work(puzzle)) {
				return true;
			}
		}
		return false; */
    }
	
	public boolean tryBacktracking() {
		Backtracking backtracking = Backtracking.getInstance();
		if (backtracking.work(puzzle)) return true;
		return false;
	}
	
}

