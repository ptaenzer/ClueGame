/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the board class. It contains all instance variables needed to make the
 * Board as well as methods to create the board and calculate adjacencies and targets.
 */

package clueGame;

import java.awt.Color;
//All imports needed for data types and files
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.sun.org.apache.bcel.internal.classfile.Field;

//Import BoardCell to use in this class
import clueGame.BoardCell;

public class Board {


	//all instance variables and constants that are needed for Board
	public static final int MAX_BOARD_SIZE = 26;
	private Map<Character, String> legend = new HashMap<Character, String>();
	private Map<String, Player> players = new HashMap<String, Player>();
	private Map<CardType, ArrayList<Card>> deck = new HashMap<CardType, ArrayList<Card>>();
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<String> weapons = new HashSet<String>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private BoardCell[][] board;
	private static Board theInstance = new Board();

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

	}
	
	//loads the weapon configuration
	//throws FileNotFoundException if file name isn't correct
	//throws BadConfigurationException if the configuration isn't correct
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
			Card newWeapon = new Card(weapon);
			deck.get(CardType.WEAPON).add(newWeapon);
		}
	}

	//loads the player configuration
	//throws FileNotFoundException if file name isn't correct
	//throws BadConfigurationException if the configuration isn't correct
	public void loadPlayerConfig() throws BadConfigException, FileNotFoundException{
		ArrayList<Card> p = new ArrayList<Card>();
		deck.put(CardType.PERSON, p);
		FileReader input = new FileReader(playerConfigFile);
		Scanner in = new Scanner(input);
		while(in.hasNext()) {
			in.useDelimiter(", ");
			String name = in.next();
			String color = in.next();
			Color c = convertColor(color);
			boolean human = false;
			in.skip(", ");
			String h = in.nextLine();
			if(h.trim().matches("Human")) {
				human = true;
			}
			Player play = new Player(name, c, human);
			players.put(name, play);
		}
		in.close();
		for(Player player : players.values()) {
			Card newPlayer = new Card(player.getPlayerName());
			deck.get(CardType.PERSON).add(newPlayer);
		}
	}

	//loads the room configuration
	//throws FileNotFoundException if file name isn't correct
	//throws BadConfigurationException if the configuration isn't correct
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
				Card newRoom = new Card(room);
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
				board[j][i].setInitial(line[i].charAt(0));

				//if room has 2 letters, sets door direction
				if(line[i].length() == 2) {
					if(line[i].charAt(1) == 'U') {
						board[j][i].setDoorDirection(DoorDirection.UP);
						board[j][i].setIsDoor();
					}
					else if(line[i].charAt(1) == 'D') {
						board[j][i].setDoorDirection(DoorDirection.DOWN);
						board[j][i].setIsDoor();
					}
					else if(line[i].charAt(1) == 'L') {
						board[j][i].setDoorDirection(DoorDirection.LEFT);
						board[j][i].setIsDoor();
					}
					else if(line[i].charAt(1) == 'R') {
						board[j][i].setDoorDirection(DoorDirection.RIGHT);
						board[j][i].setIsDoor();
					}
					else if(line[i].charAt(1) == 'C') {
						board[j][i].setDoorDirection(DoorDirection.CORNER);
						board[j][i].setIsDoor();
					}
				}
				else {
					board[j][i].setDoorDirection(DoorDirection.NONE);
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

	//calcAdjacencies now returns a set, which is used to populate the map
	//No longer clear adj, rather create new adjs every time for every board cell.

	//calculates the adjacent cells that a piece can move to, based on the cell passed
	public Set<BoardCell> calcAdjacencies(BoardCell currentCell) {
		int row = currentCell.getRow();
		int column = currentCell.getColumn();
		Set<BoardCell> adjs = new HashSet<BoardCell>();
		//checks cell above
		if(row > 0) {
			if (currentCell.isDoorway()) {
				if(currentCell.getDoorDirection() == DoorDirection.UP) {
					BoardCell up = board[row - 1][column];
					adjs.add(up);
				}
			}
			else if ((board[row - 1][column].getInitial() == 'W' && board[row - 1][column].getInitial() == board[row][column].getInitial()) || board[row - 1][column].getDoorDirection() == DoorDirection.DOWN) {
				BoardCell up = board[row - 1][column];
				adjs.add(up);
			}
		}

		//checks cell below
		if(row < MAX_BOARD_SIZE -1 ) {
			if (currentCell.isDoorway()) {
				if(currentCell.getDoorDirection() == DoorDirection.DOWN) {
					BoardCell down = board[row + 1][column];
					adjs.add(down);
				}
			}
			else if ((board[row + 1][column].getInitial() == 'W' && board[row + 1][column].getInitial() == board[row][column].getInitial())|| board[row + 1][column].getDoorDirection() == DoorDirection.UP) {
				BoardCell down = board[row + 1][column];
				adjs.add(down);
			}
		}

		//checks cell to left
		if(column > 0) {
			if (currentCell.isDoorway()) {
				if(currentCell.getDoorDirection() == DoorDirection.LEFT) {
					BoardCell left = board[row][column - 1];
					adjs.add(left);
				}
			}
			else if ((board[row][column - 1].getInitial() == 'W' && board[row][column - 1].getInitial() == board[row][column].getInitial()) || board[row][column - 1].getDoorDirection() == DoorDirection.RIGHT) {
				BoardCell left = board[row][column - 1];
				adjs.add(left);
			}
		}

		//checks cell to right
		if(column < MAX_BOARD_SIZE-1) {
			if (currentCell.isDoorway()) {
				if(currentCell.getDoorDirection() == DoorDirection.RIGHT) {
					BoardCell right = board[row][column + 1];
					adjs.add(right);
				}
			}
			else if ((board[row][column + 1].getInitial() == 'W' && board[row][column + 1].getInitial() == board[row][column].getInitial()) || board[row][column + 1].getDoorDirection() == DoorDirection.LEFT) {
				BoardCell right = board[row][column + 1];
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
	public Map<Character, String> getLegend() {
		return legend;
	}

	//sets the configuration files to user specified files
	public void setConfigFiles(String string, String string2) {
		boardConfigFile = string;
		roomConfigFile = string2;
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
		// TODO Auto-generated method stub
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
	public Map<String, Player> getPlayers() {
		// TODO Auto-generated method stub
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
	public boolean checkAccusation(Solution accusation) {
		return false;
	}

	public Map<CardType, ArrayList<Card>> getDeck() {
		// TODO Auto-generated method stub
		return deck;
	}

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
}
