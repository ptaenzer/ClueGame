/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the human player class used for all users
 */
package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class HumanPlayer extends Player {

	//constructor
	public HumanPlayer(String playerName, Color color, boolean human) {
		super(playerName, color, human);
	}

	// overridden abstract functions from player
	// createSuggestion(), makeSug() and makeAccusation() not used for human player
	@Override
	public void move(int roll, String currentName) {
		Board.setHumanMove(false);
		Set<BoardCell> targets = Board.getTargets();
		Board.changeColorTargets();
	}
	
	public void makeSug() {
		
	}

	@Override
	protected ArrayList<Card> createSuggestion(Card card) {
		return this.suggestion;
	}

	@Override
	protected void makeAccusation() {
		
	}
} 