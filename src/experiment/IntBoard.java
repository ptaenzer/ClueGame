/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the board class that contains most of the actual code and methods for the game.
 */
package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;

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
		visited.clear();
		targets.clear();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}

	private void findAllTargets(BoardCell thisCell, int numSteps) {
		adj = getAdjList(thisCell);
		for(BoardCell n : adj) {
			if(!visited.contains(n)){
				visited.add(n);
				if(numSteps == 1) {
					targets.add(n);
				}
				else {
					findAllTargets(n, numSteps-1);
				}
			}
			visited.remove(n);
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
