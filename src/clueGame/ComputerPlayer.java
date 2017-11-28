/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the computer player class used for all ai 
 */
package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

	private char justVisited;
	private Card justVisitedCard;

	// constructor
	public ComputerPlayer(String playerName, Color color, boolean human) {
		super(playerName, color, human);
	}

	// picks location based on given targets
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell location = null;
		ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
		// picks doorway if not last visited
		for(BoardCell test : targets) {
			if(test.isDoorway() && test.getInitial() != justVisited) {
				location = test;
			}
			cells.add(test);
		}
		// picks location randomly
		if(location == null) {
			Random rand = new Random();
			int r = rand.nextInt(cells.size());
			location = cells.get(r);
		}
		return location;
	}

	public void makeAccusation() {
		
	}

	// creates the suggestion 
	public ArrayList<Card> createSuggestion(Card room) {
		suggestion.clear();
		// add player
		Card person = null;
		ArrayList<Card> unSeenP = Board.getUnSeenDeck().get(CardType.PERSON);
		while(person == null) {
			Random rand = new Random();
			int r = rand.nextInt(unSeenP.size());
			if(!noteCard.contains(unSeenP.get(r))) {
				person = unSeenP.get(r);
			}
		}
		suggestion.add(person);
		// add weapon
		Card weapon = null;
		ArrayList<Card> unSeenW = Board.getUnSeenDeck().get(CardType.WEAPON);
		while(weapon == null) {
			Random rand = new Random();
			int r = rand.nextInt(unSeenW.size());
			if(!noteCard.contains(unSeenW.get(r))) {
				weapon = unSeenW.get(r);
			}
		}
		suggestion.add(weapon);
		// add room
		suggestion.add(room);
		// calls dissprove loop
		Board.dissproveLoop(suggestion, this);
		
		return suggestion;
	}

	// sets the just visited room
	public void setJustVisited(char c) {
		this.justVisited = c;
		ArrayList<Card> cards = Board.getDeck().get(CardType.ROOM);
		for(Card card : cards) {
			if(Board.getLegend().get(c) == card.getCardName()) {
				this.justVisitedCard = card;
				break;
			}
		}
	}

	// getter for just visited card
	public Card getJustVisitedCard() {
		return justVisitedCard;
	}

	@Override
	public void move(int roll, String currentName) {
		Set<BoardCell> targets = Board.getTargets();
		BoardCell newLocation = pickLocation(targets);
		Board.getPlayers().get(currentName).setColumn(newLocation.getColumn());
		Board.getPlayers().get(currentName).setRow(newLocation.getRow());
	}
}
