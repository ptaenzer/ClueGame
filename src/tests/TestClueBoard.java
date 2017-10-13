package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class TestClueBoard {
	
	// Constants that will be used in multiple methods
		public static final int LEGEND_SIZE = 11;
		public static final int NUM_ROWS = 26;
		public static final int NUM_COLUMNS = 26;

		private static Board board;
		
		@BeforeClass
		public static void setUp() {
			
			board = Board.getInstance();
			// set up file names
			board.setConfigFiles("Clue Layout.xlsx", "ClueLayoutLegend.txt");		
			board.initialize();
		}
		@Test
		public void testRooms() {
			
			Map<Character, String> legend = board.getLegend();
			// Test for correct number of rooms
			assertEquals(LEGEND_SIZE, legend.size());
			// Test rooms to make sure the files were imported correctly
			assertEquals("Closet", legend.get('C'));
			assertEquals("Gazibo", legend.get('G'));
			assertEquals("Billiard", legend.get('B'));
			assertEquals("Dungeon", legend.get('D'));
			assertEquals("Walkway", legend.get('W'));
		}
		
		@Test
		public void testBoardDimensions() {
			// Make sure board is correct size
			assertEquals(NUM_ROWS, board.getNumRows());
			assertEquals(NUM_COLUMNS, board.getNumColumns());		
		}
		
		// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN/CORNER) and a couple non-doors
		@Test
		public void DoorDirections() {
			BoardCell room = board.getCellAt(23, 23);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCellAt(4, 24);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection());
			room = board.getCellAt(18, 20);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			room = board.getCellAt(12, 15);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			room = board.getCellAt(5, 4);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.CORNER, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getCellAt(11, 3);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(0, 6);
			assertFalse(cell.isDoorway());		
		}
		
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(13, numDoors);
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('G', board.getCellAt(0, 0).getInitial());
			assertEquals('E', board.getCellAt(19, 0).getInitial());
			assertEquals('B', board.getCellAt(19, 13).getInitial());
			// Test last cell in room
			assertEquals('O', board.getCellAt(15, 6).getInitial());
			assertEquals('K', board.getCellAt(3, 25).getInitial());
			// Test a walkway
			assertEquals('W', board.getCellAt(8, 8).getInitial());
			// Test a room space
			assertEquals('F', board.getCellAt(10, 16).getInitial());
			// Test the closet
			assertEquals('C', board.getCellAt(12,11).getInitial());
		}

}
