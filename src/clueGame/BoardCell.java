/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the cell class that contains the code and methods for the cells on the board.
 */
package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	//declare all instance variables needed for each cell
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	boolean isDoor;
	private Set<BoardCell> adj = new HashSet<BoardCell>();

	//constructor for BoardCell
	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.isDoor = false;
	}
	
	public void setAdj(Set<BoardCell> cells) {
		adj = cells;
	}
	
	public Set<BoardCell> getAdj(){
		return adj;
	}

	//returns the row that the cell is in
	public int getRow() {
		// TODO Auto-generated method stub
		return row;
	}

	//returns the column that the cell is in
	public int getColumn() {
		// TODO Auto-generated method stub
		return column;
	}
	
	//sets the row for this cell
	public void setRow(int row) {
		this.row = row;
	}

	//sets the column for this cell
	public void setColumn(int column) {
		this.column = column;
	}
	
	//sets the initial for this cell
	public void setInitial(char i) {
		this.initial = i;
	}
	
	//sets the door direction for this cell
	public void setDoorDirection(DoorDirection d) {
		this.direction = d;
	}
	
	//sets boolean isDoor to true if it is a door
	public void setIsDoor() {
		this.isDoor = true;
	}
	
	//returns isDoor for cell that called the method
	public boolean isDoorway() {
		return isDoor;
	}

	//returns the initial for the cell that called the method
	public char getInitial() {
		return initial;
	}

	//returns the door direction for the cell that called the method
	public DoorDirection getDoorDirection() {
		return direction;
	}
}
