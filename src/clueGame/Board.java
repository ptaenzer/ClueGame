package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

public class Board {

	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 23;
	private Map<Character, String> legend = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> b = new HashSet<BoardCell>();
	private Set<BoardCell> adj = new HashSet<BoardCell>();
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private static Board theInstance = new Board();

	private Board() {}

	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE - 1];
		for(int i = 0; i < MAX_BOARD_SIZE; i++) {
			for (int j = 0; j < MAX_BOARD_SIZE - 1; j++) {
				board[i][j] = new BoardCell(i, j, ' ');
			}
		}
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (FileNotFoundException | BadConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadRoomConfig() throws BadConfigException, FileNotFoundException{
		FileReader input = new FileReader("CR_ClueLegend.txt");
		Scanner in = new Scanner(input);
		while(in.hasNext()) {
			in.useDelimiter(", ");
			String hold = in.next();
			char c = hold.charAt(0);
			String room = in.next();
			String type = in.nextLine();
			legend.put(c, room);
		}
		in.close();
	}

	public void loadBoardConfig() throws BadConfigException, FileNotFoundException{
		FileReader input = new FileReader("CR_ClueLayout.csv");
		Scanner in = new Scanner(input);
		int j = 0;
		while(in.hasNext()) {
			in.useDelimiter(",");
			String hold = in.nextLine();
			String[] line = hold.split(",");
			for(int i = 0; i < line.length; i++) {
				board[i][j].setInitial(line[i].charAt(0));
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
					else {
						board[i][j].setDoorDirection(DoorDirection.NONE);
					}
				}
			}
			j++;
		}
		in.close();
	}

	public void calcAdjacencies(BoardCell currentCell) {

	}

	public void calcTargets(BoardCell startCell, int pathLength) {

	}

	private void findAllTargets(BoardCell thisCell, int numSteps) {

	}

	public Set<BoardCell> getTargets(){
		return null;
	}

	public Set<BoardCell> getAdjList(BoardCell cell){
		return null;
	}

	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}

	public int getNumRows() {
		return board[0].length;
	}

	public int getNumColumns() {
		return board.length;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public void setConfigFiles(String string, String string2) {

		boardConfigFile = string;
		roomConfigFile = string2;

	}

}
