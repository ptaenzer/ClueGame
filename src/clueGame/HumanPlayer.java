/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the human player class used for all users
 */
package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {

	//constructor
	public HumanPlayer(String playerName, Color color, boolean human) {
		super(playerName, color, human);
	}

	@Override
	public void move(int roll) {
		Set<BoardCell> targets = Board.getTargets();
		
	}
} 