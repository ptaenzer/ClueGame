/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the cell class that contains the code and methods for the cells on the board.
 */
package clueGame;

public class BoardCell {
	
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	boolean isDoor;

	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.isDoor = false;
	}

	public int getRow() {
		// TODO Auto-generated method stub
		return row;
	}

	public int getColumn() {
		// TODO Auto-generated method stub
		return column;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setInitial(char i) {
		this.initial = i;
	}
	
	public void setDoorDirection(DoorDirection d) {
		this.direction = d;
	}
	
	public void setIsDoor() {
		this.isDoor = true;
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return isDoor;
	}

	public char getInitial() {
		return this.initial;
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}

}
