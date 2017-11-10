package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	private boolean human;
	private ArrayList<Card> cards = new ArrayList<Card>();
	protected ArrayList<Card> suggestion = new ArrayList<Card>();
	protected ArrayList<Card> noteCard = new ArrayList<Card>();
	protected boolean makeAccusation = false;
	
	public Player(String playerName, Color color, boolean human) {
		super();
		this.playerName = playerName;
		this.color = color;
		this.human = human;
	}

	public Card disproveSuggestion(ArrayList<Card> suggestion) {
		ArrayList<Card> matching = new ArrayList<Card>();
		for(int i = 0; i < suggestion.size(); i++) {
			for(int j = 0; j < this.cards.size(); j++) {
				if(suggestion.get(i).equals(this.cards.get(j))) {
					matching.add(suggestion.get(i));
				}
			}
		}
		if(matching.size() == 0) {
			return null;
		}
		Random rand = new Random();
		int r = rand.nextInt(matching.size());
		return matching.get(r);
	}
	
	public boolean isHuman() {
		return human;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		for(Card card : cards) {
			noteCard.add(card);
		}
		noteCard.add(new Card(playerName, CardType.PERSON));
		this.cards = cards;
	}
	
	public void addSeenCard(Card card) {
		noteCard.add(card);
	}
	
	public ArrayList<Card> getSeen() {
		return noteCard;
	}

	public void makeAccusationTrue() {
		this.makeAccusation = true;
	}
	
}
