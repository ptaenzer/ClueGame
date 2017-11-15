/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the cell class that contains the code and methods for the cells on the board.
 */
package clueGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class BoardCell extends Component{
	//declare all instance variables needed for each cell
	private int row;
	private int column;
	public static int width = 20;
	public static int height = 20;
	public static int margin = 1;
	private int rowP;
	private int columnP;
	private char initial;
	private Color color;
	private DoorDirection direction;
	boolean isDoor;
	//private Graphics box;


	//constructor for BoardCell
	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.isDoor = false;
		this.rowP = row*width + margin*(row);
		this.columnP = column*width + margin*(column);
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
		switch (i) {
		case 'W':
			this.color = Color.WHITE;
			break;
		case 'C':
			this.color = Color.BLACK;
			break;
		case 'G':
			this.color = Color.GREEN;
			break;
		case 'E':
			this.color = Color.ORANGE;
			break;
		case 'F':
			this.color = Color.LIGHT_GRAY;
			break;
		case 'O':
			this.color = Color.YELLOW;
			break;
		case 'M':
			this.color = Color.MAGENTA;
			break;
		case 'K':
			this.color = Color.CYAN;
			break;
		case 'B':
			this.color = Color.RED;
			break;
		case 'L':
			this.color = Color.GRAY;
			break;
		case 'D':
			this.color = Color.PINK;
			break;
		default:
			break;
		}
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

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(rowP, columnP, width, height);
		repaint();
	}

	//public Graphics getGraph() {
	//	return box;
	//}
}
