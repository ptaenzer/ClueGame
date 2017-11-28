/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the cell class that contains the code and methods for the cells on the board.
 */
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.*;

import com.sun.prism.BasicStroke;

public class BoardCell {
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
			float[] c = Color.RGBtoHSB(0, 153, 0, null);
			Color walkway = Color.getHSBColor(c[0], c[1], c[2]);
			this.color = walkway;
			break;
		case 'C':
			this.color = Color.BLACK;
			break;
		default:
			float[] c2 = Color.RGBtoHSB(76, 0, 153, null);
			Color rooms = Color.getHSBColor(c2[0], c2[1], c2[2]);
			this.color = rooms;
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

	// draw function called by paint component
	public void draw(Graphics g) {
		// draw rectangle
		g.setColor(color);
		g.fillRect(rowP, columnP, width, height);
		// draw door
		g.setColor(Color.WHITE);
		switch(direction) {
		case UP:
			g.drawLine(rowP, columnP, (rowP+width), columnP);
			break;
		case DOWN:
			g.drawLine(rowP, (columnP+height), (rowP+width), (columnP+height));
			break;
		case LEFT:
			g.drawLine(rowP, columnP, rowP, (columnP+height));
			break;
		case RIGHT:
			g.drawLine((rowP+width), columnP, (rowP+width), (columnP+height));
			break;
		case NONE:
			break;
		default:
			break;
		}

	}

	public void setColor(Color cyan) {
		this.color = cyan;
	}
}
