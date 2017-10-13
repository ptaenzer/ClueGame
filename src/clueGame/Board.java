package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class Board {

	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 26;
	private Map<Character, String> legend = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> b = new HashSet<BoardCell>();
	private Set<BoardCell> adj = new HashSet<BoardCell>();
	private BoardCell[][] board;
	private static Board theInstance = new Board();

	private Board() {}

	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		for(int i = 0; i < MAX_BOARD_SIZE; i++) {
			for (int j = 0; j < MAX_BOARD_SIZE; j++) {
				board[i][j] = new BoardCell(i, j, ' ');
			}
		}
	}

	public void loadRoomConfig() throws BadConfigException{

	}

	public void loadBoardConfig() throws BadConfigException{

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
		return 0;
	}

	public int getNumColumns() {
		return 0;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public void setConfigFiles(String string, String string2) {
		
		boardConfigFile = string;
		roomConfigFile = string2;
		
	}

}
