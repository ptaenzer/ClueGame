package experiment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> board;

	public IntBoard(Map<BoardCell, Set<BoardCell>> board) {
		super();
		this.board = board;
	}

	public void calcAdjacencies() {

	}

	public void calcTargets(BoardCell startCell, int pathLength) {

	}

	public Set<BoardCell> getTargets(){

		Set<BoardCell> targets = new HashSet<BoardCell>();

		return targets;
	}

	public Set<BoardCell> getAdjList(BoardCell cell){

		Set<BoardCell> adj = new HashSet<BoardCell>();

		return adj;
	}

	public BoardCell getCell(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

}
