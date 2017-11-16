/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the board class. It contains all instance variables needed to make the
 * Board as well as methods to create the board and calculate adjacencies and targets.
 */

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
//All imports needed for data types and files
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.sun.javafx.charts.Legend;

//Import BoardCell to use in this class
import clueGame.BoardCell;

public class Board extends JPanel{


	//all instance variables and constants that are needed for Board
	public static final int MAX_BOARD_SIZE = 26;
	private static Map<Character, String> legend = new HashMap<Character, String>();
	private static Map<String, Player> players = new HashMap<String, Player>();
	private static Map<CardType, ArrayList<Card>> deck = new HashMap<CardType, ArrayList<Card>>();
	private static Map<CardType, ArrayList<Card>> unSeenDeck = new HashMap<CardType, ArrayList<Card>>();
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private static String humanName;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private static Set<String> weapons = new HashSet<String>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private static BoardCell[][] board;
	private static Board theInstance = new Board();
	private static String[] peopleNames;

	private Board() {}

	//returns instance of the Board for use in tests
	public static Board getInstance() {
		return theInstance;
	}

	//initializes the board to the correct size, creates board cell in every location
	//loads in files and catches errors
	public void initialize() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		for(int i = 0; i < MAX_BOARD_SIZE; i++) {
			for (int j = 0; j < MAX_BOARD_SIZE; j++) {
				board[i][j] = new BoardCell(i, j, ' ');
			}
		}
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayerConfig();
			loadWeaponConfig();
		} catch (FileNotFoundException | BadConfigException e) {
			e.printStackTrace();
		}

		deal();
		createBoardPanel();
	}

	//loads the weapon configuration
	//throws FileNotFoundException if file name isn't correct
	//throws BadConfigurationException if the configuration isn't correct
	//loads the weapon set of the deck map.
	public void loadWeaponConfig() throws BadConfigException, FileNotFoundException{
		ArrayList<Card> w = new ArrayList<Card>();
		deck.put(CardType.WEAPON, w);
		FileReader input = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(input);
		while(in.hasNext()) {
			String name = in.nextLine();
			weapons.add(name);
		}
		in.close();
		for(String weapon : weapons) {
			Card newWeapon = new Card(weapon, CardType.WEAPON);
			deck.get(CardType.WEAPON).add(newWeapon);
		}
	}

	//loads the player configuration
	//throws FileNotFoundException if file name isn't correct
	//throws BadConfigurationException if the configuration isn't correct
	//loads the player map into the deck map
	public void loadPlayerConfig() throws BadConfigException, FileNotFoundException{
		ArrayList<Card> p = new ArrayList<Card>();
		deck.put(CardType.PERSON, p);
		FileReader input = new FileReader(playerConfigFile);
		Scanner in = new Scanner(input);
		int i = 0;
		while(in.hasNext()) {
			in.useDelimiter(", ");
			String name = in.next();
			in.skip(", ");
			String color = in.nextLine();
			Color c = convertColor(color.trim());
			ComputerPlayer play = new ComputerPlayer(name, c, false);
			players.put(name, play);
			players.get(name).setPlayerColor(color);
			i++;
		}
		peopleNames = new String[i];
		i = 0;
		for(String name : players.keySet()) {
			peopleNames[i] = name;
			i++;
		}
		in.close();
		for(Player player : players.values()) {
			Card newPlayer = new Card(player.getPlayerName(), CardType.PERSON);
			deck.get(CardType.PERSON).add(newPlayer);
		}
	}

	//loads the room configuration
	//throws FileNotFoundException if file name isn't correct
	//throws BadConfigurationException if the configuration isn't correct
	//loads the Room map into the Deck map
	public void loadRoomConfig() throws BadConfigException, FileNotFoundException{
		ArrayList<Card> r = new ArrayList<Card>();
		deck.put(CardType.ROOM, r);
		FileReader input = new FileReader(roomConfigFile);
		Scanner in = new Scanner(input);
		while(in.hasNext()) {
			in.useDelimiter(", ");
			String hold = in.next();
			char c = hold.charAt(0);
			String room = in.next();
			in.skip(", ");
			String type = in.nextLine();
			legend.put(c, room);
			if(type.equalsIgnoreCase("Card")) {
				Card newRoom = new Card(room, CardType.ROOM);
				deck.get(CardType.ROOM).add(newRoom);
			}
		}
		in.close();
	}

	//loads the board configuration
	//throws FileNotFoundException if file name isn't correct
	//throws BadConfigurationException if the configuration isn't correct
	public void loadBoardConfig() throws BadConfigException, FileNotFoundException{
		FileReader input = new FileReader(boardConfigFile);
		Scanner in = new Scanner(input);
		int j = 0;
		while(in.hasNext()) {
			String hold = in.nextLine();
			String[] line = hold.split(",");
			for(int i = 0; i < line.length; i++) {
				board[i][j].setInitial(line[i].charAt(0));
				//if room has 2 letters, sets door direction
				if(line[i].length() == 2) {
					if(line[i].charAt(1) == 'U') {
						board[i][j].setDoorDirection(DoorDirection.UP);
						board[i][j].setIsDoor();
					}
					else if(line[i].charAt(1) == 'D') {
						board[i][j].setDoorDirection(DoorDirection.DOWN);
						board[i][j].setIsDoor();
					}
					else if(line[i].charAt(1) == 'L') {
						board[i][j].setDoorDirection(DoorDirection.LEFT);
						board[i][j].setIsDoor();
					}
					else if(line[i].charAt(1) == 'R') {
						board[i][j].setDoorDirection(DoorDirection.RIGHT);
						board[i][j].setIsDoor();
					}
					else if(line[i].charAt(1) == 'C') {
						board[i][j].setDoorDirection(DoorDirection.CORNER);
						board[i][j].setIsDoor();
					}
				}
				else {
					board[i][j].setDoorDirection(DoorDirection.NONE);
				}
			}
			j++;
		}
		in.close();
		//calculates adjacencies for all cells
		for (int i = 0; i < MAX_BOARD_SIZE; i++) {
			for (int k = 0; k < MAX_BOARD_SIZE; k++) {
				adjMtx.put(board[i][k], calcAdjacencies(board[i][k]));
			}
		}
	}

	//Deals deck to all players
	private void deal() {
		// sets un-seen deck
		unSeenDeck = deck;
		// creates random deck
		int deckSize = deck.get(CardType.ROOM).size() + deck.get(CardType.PERSON).size() + deck.get(CardType.WEAPON).size();
		ArrayList<Card> randomDeck = new ArrayList<Card>();
		for(int i = 0; i < deckSize; i++) {
			randomDeck.add(null);
		}
		Random rand = new Random();
		for(CardType c : deck.keySet()) {
			for(Card card : deck.get(c)) {
				int r = rand.nextInt(deckSize);
				while(randomDeck.get(r) != null) {
					r = rand.nextInt(deckSize);
				}
				randomDeck.set(r, card);
			}
		}
		// deals solution
		boolean killer = false;
		boolean weapon = false;
		boolean room = false;	
		int k = 0;
		while(killer == false) {
			if(randomDeck.get(k).getCardType() == CardType.PERSON) {
				Solution.setPerson(randomDeck.get(k));
				killer = true;
				deck.get(CardType.PERSON).remove(randomDeck.get(k));
				randomDeck.remove(k);
			}
			k++;
		}
		k = 0;
		while(weapon == false) {
			if(randomDeck.get(k).getCardType() == CardType.WEAPON) {
				Solution.setWeapon(randomDeck.get(k));
				weapon = true;
				deck.get(CardType.WEAPON).remove(randomDeck.get(k));
				randomDeck.remove(k);
			}
			k++;
		}
		k = 0;
		while(room == false) {
			if(randomDeck.get(k).getCardType() == CardType.ROOM) {
				Solution.setRoom(randomDeck.get(k));
				room = true;
				deck.get(CardType.ROOM).remove(randomDeck.get(k));
				randomDeck.remove(k);
			}
			k++;
		}
		// deals to players
		int j = 0;
		for(String name : players.keySet()) {
			ArrayList<Card> cards = new ArrayList<Card>();
			for(int i = 0; i < (randomDeck.size()/(players.size())); i++) {
				cards.add(randomDeck.get(j));
				j++;
			}
			players.get(name).setCards(cards);
		}
	}

	//calcAdjacencies now returns a set, which is used to populate the map
	//No longer clear adj, rather create new adjs every time for every board cell.

	//calculates the adjacent cells that a piece can move to, based on the cell passed
	public Set<BoardCell> calcAdjacencies(BoardCell currentCell) {
		int row = currentCell.getRow();
		int column = currentCell.getColumn();
		Set<BoardCell> adjs = new HashSet<BoardCell>();
		//checks cell above
		if(column > 0) {
			if (currentCell.isDoorway()) {
				if(currentCell.getDoorDirection() == DoorDirection.UP) {
					BoardCell up = board[row][column - 1];
					adjs.add(up);
				}
			}
			else if ((board[row][column - 1].getInitial() == 'W' && board[row][column - 1].getInitial() == board[row][column].getInitial()) || board[row][column - 1].getDoorDirection() == DoorDirection.DOWN) {
				BoardCell up = board[row][column - 1];
				adjs.add(up);
			}
		}

		//checks cell below
		if(column < MAX_BOARD_SIZE -1 ) {
			if (currentCell.isDoorway()) {
				if(currentCell.getDoorDirection() == DoorDirection.DOWN) {
					BoardCell down = board[row][column + 1];
					adjs.add(down);
				}
			}
			else if ((board[row][column + 1].getInitial() == 'W' && board[row][column + 1].getInitial() == board[row][column].getInitial())|| board[row][column + 1].getDoorDirection() == DoorDirection.UP) {
				BoardCell down = board[row][column + 1];
				adjs.add(down);
			}
		}

		//checks cell to left
		if(row > 0) {
			if (currentCell.isDoorway()) {
				if(currentCell.getDoorDirection() == DoorDirection.LEFT) {
					BoardCell left = board[row - 1][column];
					adjs.add(left);
				}
			}
			else if ((board[row - 1][column].getInitial() == 'W' && board[row - 1][column].getInitial() == board[row][column].getInitial()) || board[row - 1][column].getDoorDirection() == DoorDirection.RIGHT) {
				BoardCell left = board[row - 1][column];
				adjs.add(left);
			}
		}

		//checks cell to right
		if(row < MAX_BOARD_SIZE-1) {
			if (currentCell.isDoorway()) {
				if(currentCell.getDoorDirection() == DoorDirection.RIGHT) {
					BoardCell right = board[row + 1][column];
					adjs.add(right);
				}
			}
			else if ((board[row + 1][column].getInitial() == 'W' && board[row + 1][column].getInitial() == board[row][column].getInitial()) || board[row + 1][column].getDoorDirection() == DoorDirection.LEFT) {
				BoardCell right = board[row + 1][column];
				adjs.add(right);
			}
		}
		return adjs;
	}

	//recursive function to find all targets available numSteps away called by calcTargets
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		visited.add(thisCell);
		for(BoardCell n : adjMtx.get(thisCell)) {
			if(visited.contains(n)){
				continue;
			}
			visited.add(n);
			if(numSteps == 1 || n.isDoorway()) {
				targets.add(n);
			}
			else {
				findAllTargets(n, numSteps-1);
			}
			visited.remove(n);
		}
	}

	//returns specific cell
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}

	//returns number of rows
	public int getNumRows() {
		return board[0].length;
	}

	//returns number of columns
	public int getNumColumns() {
		return board.length;
	}

	//returns the legend
	public static Map<Character, String> getLegend() {
		return legend;
	}

	//gets the adjacency list for current cell
	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMtx.get(board[i][j]);
	}

	//calculates targets for current cell that are k steps away
	public void calcTargets(int i, int j, int k) {
		BoardCell cell = board[i][j];
		visited.clear();
		targets.clear();
		findAllTargets(cell, k);
	}

	//returns the targets for the cell
	public Set<BoardCell> getTargets() {
		return targets;
	}

	//Sets the correct configuration files
	public void setConfigFiles(String string, String string2, String string3, String string4) {
		boardConfigFile = string;
		roomConfigFile = string2;
		playerConfigFile = string3;
		weaponConfigFile = string4;
	}

	//Returns a map of players
	public static Map<String, Player> getPlayers() {
		return players;
	}

	//Selects the killer, weapon, and room
	public void selectAnswer() {

	}

	//Handles Suggestions??
	public Card handleSuggestions() {
		return null;
	}

	//Checks if the accusation matches the answer
	public boolean checkAccusation(ArrayList<Card> accusation) {
		return Solution.testAccusation(accusation.get(0), accusation.get(1), accusation.get(2));
	}

	// getter for deck
	public static Map<CardType, ArrayList<Card>> getDeck() {
		return deck;
	}

	// color converter
	public Color convertColor(String strColor) {
		Color color; 
		try {     // We can use reflection to convert the string to a color
			java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} 
		catch (Exception e) {  color = null; // Not defined
		}
		return color;
	}

	// getter for unseen deck
	public static Map<CardType, ArrayList<Card>> getUnSeenDeck() {
		// TODO Auto-generated method stub
		return unSeenDeck;
	}

	// dissprove loop called after evry suggestion
	public static Card dissproveLoop(ArrayList<Card> sug, Player person) {
		Set<String> people = players.keySet();
		ArrayList<String> order = new ArrayList<String>();
		// finds the player that suggested
		int i = 0;
		for(String p : people) {
			if(p.equals(person.getPlayerName())) {
				break;
			}
			i++;
		}

		// adds everyone after suggestion person
		int j = 0;
		for(String p : people) {
			if(j > i) {
				order.add(p);
			}
			j++;
		}

		// adds everyone before suggestion person
		int k = 0;
		for(String p : people) {
			if(k < i) {
				order.add(p);
			}
			k++;
		}

		// finds if a player can disprove and gets disproving card
		Card disprove = null;
		for(String p : order) {
			disprove = players.get(p).disproveSuggestion(sug);
			if(disprove != null) {
				break;
			}
		}

		// if no one can disprove then sets make Acusation of that player to true
		if(disprove == null) {
			person.makeAccusationTrue();
		}

		return disprove;
	}

	// getter for weapons list
	public static Set<String> getWeapons() {
		return weapons;
	}
	
	//getter for board
	public static BoardCell[][] getBoard() {
		return board;
	}
	
	// panel creator called in gui and initialize
	public JPanel createBoardPanel() {
		int width  = board.length*BoardCell.width + board.length*BoardCell.margin;
		int height  = board.length*BoardCell.height + board.length*BoardCell.margin;
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		return this;
	}

	// pain component for board panel
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		// draw board cells
		for(int i = 0; i < MAX_BOARD_SIZE; i++) {
			for(int j = 0; j < MAX_BOARD_SIZE; j++) {
				board[i][j].draw(g);
			}
		}
		// draw players
		for(String name: players.keySet()) {
			players.get(name).draw(g);
		}
		// draw names
		Font f = new Font("Comic Sans MS", Font.BOLD, 18);
        g.setFont(f);
        g.setColor(Color.WHITE);
		for(int i = 0; i < MAX_BOARD_SIZE; i++) {
			for(int j = 0; j < MAX_BOARD_SIZE; j++) {
				if(board[i][j] == board[1][2] ||board[i][j] == board[10][3] || board[i][j] == board[20][5] || board[i][j] == board[1][12] || board[i][j] == board[9][17] || board[i][j] == board[20][18] || board[i][j] == board[1][22] || board[i][j] == board[9][24] || board[i][j] == board[18][25]) {
					g.drawString(legend.get(board[i][j].getInitial()), (i*20+ i), (j*20 + j));
				}
			}
		}
	}

	// setter for human name
	public static void setHumanName(String name) {
		humanName = name;
		Color color = players.get(name).getColor();
		String c = players.get(name).getPlayerColor();
		ArrayList<Card> cards = players.get(name).getCards();
		players.remove(name);
		Player human = new HumanPlayer(name, color, true);
		human.setCards(cards);
		human.setPlayerColor(c);
		players.put(name, human);
	}

	// getter for peoples names
		public static String getHumanName() {
			return humanName;
		}
		
	// getter for peoples names
	public static String[] getPeopleNames() {
		return peopleNames;
	}
}
