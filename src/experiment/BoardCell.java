/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the cell class that contains the code and methods for the cells on the board.
 */
package experiment;

public class BoardCell {
	
	private int row;
	private int column;

	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
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

}
