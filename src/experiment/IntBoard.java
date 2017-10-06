package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> board = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> b = new HashSet<BoardCell>();
	private Set<BoardCell> adj = new HashSet<BoardCell>();
	private BoardCell[][] grid;

	public IntBoard(Map<BoardCell, Set<BoardCell>> board) {
		super();
		createBoard();
		this.board = board;
	}

	public void createBoard() {
		grid = new BoardCell[4][4];
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				grid[i][j] = new BoardCell(i, j);
				board.put(grid[i][j], b);
			}
		}
	}
	
	public void calcAdjacencies(BoardCell currentCell) {
		
		int row = currentCell.getRow();
		int column = currentCell.getColumn();
		
		adj.clear();
		
		if(row > 0) {
			BoardCell up = grid[row - 1][column];
			adj.add(up);
		}
		if(row < 3) {
			BoardCell down = grid[row + 1][column];
			adj.add(down);
		}
		if(column > 0) {
			BoardCell left = grid[row][column - 1];
			adj.add(left);
		}
		if(column < 3) {
			BoardCell right = grid[row][column + 1];
			adj.add(right);
		}
		
	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}

	private void findAllTargets(BoardCell thisCell, int numSteps) {
		if(numSteps == 0) {
			targets.add(thisCell);
		}
		else {
			calcAdjacencies(thisCell);
			Iterator<BoardCell> a = adj.iterator();
			while(a.hasNext()) {
				BoardCell n = a.next();
				if(!visited.contains(n)){
					visited.add(n);
					findAllTargets(n, numSteps-1);
				}
			}
		}
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public Set<BoardCell> getAdjList(BoardCell cell){
		calcAdjacencies(cell);
		return adj;
	}

	public BoardCell getCell(int i, int j) {
		BoardCell b = grid[i][j];
		return b;
	}

}
