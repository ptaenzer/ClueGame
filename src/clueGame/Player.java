/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the player class used for human and computer players 
 */
package clueGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class Player {

	// member variables
	private String playerName;
	private String playerColor;
	protected int row;
	protected int column;
	private Color color;
	private boolean human;
	public static int width = 20;
	public static int height = 20;
	public static int margin = 1;
	private int rowP;
	private int columnP;
	private ArrayList<Card> cards = new ArrayList<Card>();
	protected ArrayList<Card> suggestion = new ArrayList<Card>();
	protected Set<Card> noteCard = new HashSet<Card>();
	protected boolean makeAccusation = false;
	
	// constructor
	public Player(String playerName, Color color, boolean human) {
		super();
		this.playerName = playerName;
		this.color = color;
		this.human = human;
		noteCard.add(new Card(playerName, CardType.PERSON));
		Random rand = new Random();
		int x = rand.nextInt(Board.MAX_BOARD_SIZE);
		int y = rand.nextInt(Board.MAX_BOARD_SIZE);
		BoardCell[][] b = Board.getBoard();
		while(b[x][y].getInitial() != 'W') {
			x = rand.nextInt(Board.MAX_BOARD_SIZE);
			y = rand.nextInt(Board.MAX_BOARD_SIZE);
		}
		this.row = x;
		this.rowP = row*width + margin*(row);
		this.column = y;
		this.columnP = column*width + margin*(column);
	}

	// disprove suggestion 
	public Card disproveSuggestion(ArrayList<Card> suggestion) {
		// finds all possible disproving cards
		ArrayList<Card> matching = new ArrayList<Card>();
		for(int i = 0; i < suggestion.size(); i++) {
			for(int j = 0; j < this.cards.size(); j++) {
				if(suggestion.get(i).equals(this.cards.get(j))) {
					matching.add(suggestion.get(i));
				}
			}
		}
		// if there are no disproving suggestions returns null
		if(matching.size() == 0) {
			return null;
		}
		// returns random card from disproving cards
		Random rand = new Random();
		int r = rand.nextInt(matching.size());
		return matching.get(r);
	}
	
	// getters and setters
	public boolean isHuman() {
		return human;
	}
	
	public void setIsHuman(boolean h) {
		this.human = h;
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
		this.rowP = row*width + margin*(row);
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
		this.columnP = column*width + margin*(column);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(String playerColor) {
		this.playerColor = playerColor;
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
	
	public Set<Card> getSeen() {
		return noteCard;
	}

	public void makeAccusationTrue() {
		this.makeAccusation = true;
	}

	public String getSuggestionString() {
		String sug = suggestion.get(0).getCardName() + " with the " + suggestion.get(1).getCardName() + " in the " + suggestion.get(2).getCardName() + " room!";
		return sug;
	}

	public ArrayList<Card> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(ArrayList<Card> sug) {
		this.suggestion = sug;
	}
	
	// draw function called by paint component
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(rowP, columnP, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(rowP, columnP, width, height);
	}

	// abstract functions used in computer player and human player
	protected abstract void move(int roll, String currentName);
	protected abstract ArrayList<Card> createSuggestion(Card card);
	protected abstract void makeAccusation();
	
}
