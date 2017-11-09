package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

	private char justVisited;
	private Card justVisitedCard;

	public ComputerPlayer(String playerName, Color color, boolean human) {
		super(playerName, color, human);
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell location = null;
		ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
		for(BoardCell test : targets) {
			if(test.isDoorway() && test.getInitial() != justVisited) {
				location = test;
			}
			cells.add(test);
		}
		if(location == null) {
			Random rand = new Random();
			int r = rand.nextInt(cells.size());
			location = cells.get(r);
		}
		return location;
	}

	public void makeAccusation() {
		
	}

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
		return suggestion;
	}

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

	public Card getJustVisitedCard() {
		return justVisitedCard;
	}

}
