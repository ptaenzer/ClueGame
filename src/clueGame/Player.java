package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	private boolean human;
	private Set<Card> cards = new HashSet<Card>(3);
	
	public Player(String playerName, Color color) {
		super();
		this.playerName = playerName;
		this.color = color;
	}

	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public boolean isHuman() {
		return false;
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

	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	
	
	
}
