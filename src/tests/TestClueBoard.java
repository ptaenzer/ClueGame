/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is our test class that tests:
 * Adjacencies
 * Targets
 * Room Creation
 * Board Cells
 */
package tests;

//imports for color
import java.awt.Color;

//imports for data types
import java.util.Map;
import java.util.Set;

//imports for junit
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

//imports for clueGame package
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.CardType;
import clueGame.DoorDirection;

public class TestClueBoard {

	// Constants that will be used in multiple methods
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 26;
	public static final int NUM_COLUMNS = 26;

	private static Board board;

	//sets up the board necessary for tests
	//only runs once
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		// set up file names
		board.setConfigFiles("ClueLayout.csv", "ClueLayoutLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
	}
	
	//tests room creation
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

	//tests board dimensions
	@Test
	public void testBoardDimensions() {
		// Make sure board is correct size
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN/CORNER) and a couple non-doors
	@Test
	public void DoorDirections() {
		
		//Tests cells that are doors
		BoardCell room = board.getCellAt(23, 23);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(4, 24);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(12, 0);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(12, 15);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(5, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		// Test cells that aren't doors
		room = board.getCellAt(11, 3);
		assertFalse(room.isDoorway());
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
		// Test a walkway cell
		assertEquals('W', board.getCellAt(8, 8).getInitial());
		// Test a room cell
		assertEquals('F', board.getCellAt(10, 16).getInitial());
		// Test a closet cell
		assertEquals('C', board.getCellAt(12, 11).getInitial());
	}

	// Ensure that player does not move around within room
	// This cell is Yellow on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		Set<BoardCell> testList = board.getAdjList(21, 17);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the walkway. 
	// These tests are Red on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(23, 23);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(24, 23)));
		//TEST DOORWAY UP
		testList = board.getAdjList(10, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 20)));
	}

	// Test adjacency at entrance to rooms
	// These tests are Orange in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(7, 12);
		assertTrue(testList.contains(board.getCellAt(7, 11)));
		assertTrue(testList.contains(board.getCellAt(7, 13)));
		assertTrue(testList.contains(board.getCellAt(8, 12)));
		assertTrue(testList.contains(board.getCellAt(6, 12)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(4, 25);
		assertTrue(testList.contains(board.getCellAt(5, 25)));
		assertTrue(testList.contains(board.getCellAt(4, 24)));
		assertEquals(2, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(11, 0);
		assertTrue(testList.contains(board.getCellAt(10, 0)));
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		assertEquals(2, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(21, 12);
		assertTrue(testList.contains(board.getCellAt(22, 12)));
		assertTrue(testList.contains(board.getCellAt(20, 12)));
		assertTrue(testList.contains(board.getCellAt(21, 11)));
		assertTrue(testList.contains(board.getCellAt(21, 13)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are Green, Blue, White on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Green tests
		// Test on top edge of board
		Set<BoardCell> testList = board.getAdjList(15, 0);
		assertTrue(testList.contains(board.getCellAt(14, 0)));
		assertTrue(testList.contains(board.getCellAt(16, 0)));
		assertEquals(2, testList.size());

		//test to make sure suspicions that getAdj returns same thing every time... :,(
		/*testList = board.getAdjList(14, 12);
		assertEquals(3, testList.size());
		*/
		
		// Test on left edge of board
		testList = board.getAdjList(0, 17);
		assertTrue(testList.contains(board.getCellAt(0, 16)));
		assertTrue(testList.contains(board.getCellAt(1, 17)));
		assertTrue(testList.contains(board.getCellAt(0, 18)));
		assertEquals(3, testList.size());

		// Test on bottom edge of board
		testList = board.getAdjList(14, 25);
		assertTrue(testList.contains(board.getCellAt(14, 24)));
		assertTrue(testList.contains(board.getCellAt(15, 25)));
		assertEquals(2, testList.size());

		// Test on right edge of board
		testList = board.getAdjList(25, 11);
		assertTrue(testList.contains(board.getCellAt(25, 10)));
		assertTrue(testList.contains(board.getCellAt(24, 11)));
		assertTrue(testList.contains(board.getCellAt(25, 12)));
		assertEquals(3, testList.size());

		// Blue Tests
		// Tests next to rooms
		testList = board.getAdjList(3, 6);
		assertTrue(testList.contains(board.getCellAt(2, 6)));
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertEquals(3, testList.size());
		
		testList = board.getAdjList(11, 19);
		assertTrue(testList.contains(board.getCellAt(10, 19)));
		assertTrue(testList.contains(board.getCellAt(12, 19)));
		assertTrue(testList.contains(board.getCellAt(11, 20)));
		assertEquals(3, testList.size());
		
		// White test
		// Test surrounded by 4 walkways
		testList = board.getAdjList(16, 20);
		assertTrue(testList.contains(board.getCellAt(15, 20)));
		assertTrue(testList.contains(board.getCellAt(17, 20)));
		assertTrue(testList.contains(board.getCellAt(16, 19)));
		assertTrue(testList.contains(board.getCellAt(16, 21)));
		assertEquals(4, testList.size());
	}


	// Tests of just walkways, 1 step
	// This is Pink on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(7, 8, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 9)));
		assertTrue(targets.contains(board.getCellAt(7, 7)));	
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(8, 8)));			
	}

	// Tests of just walkways, 2 steps
	// These are Pink on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(16, 11, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 10)));
		assertTrue(targets.contains(board.getCellAt(15, 12)));	
		assertTrue(targets.contains(board.getCellAt(17, 10)));
		assertTrue(targets.contains(board.getCellAt(17, 12)));	
		assertTrue(targets.contains(board.getCellAt(18, 11)));
		assertTrue(targets.contains(board.getCellAt(16, 9)));
		assertTrue(targets.contains(board.getCellAt(16, 13)));		
	}

	// Tests of just walkways, 4 steps
	// These are Pink on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(2, 16, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 15)));
		assertTrue(targets.contains(board.getCellAt(3, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		assertTrue(targets.contains(board.getCellAt(0, 16)));
		assertTrue(targets.contains(board.getCellAt(4, 16)));
		assertTrue(targets.contains(board.getCellAt(6, 16)));
		assertTrue(targets.contains(board.getCellAt(1, 17)));
		assertTrue(targets.contains(board.getCellAt(3, 17)));
		assertTrue(targets.contains(board.getCellAt(5, 17)));
		assertTrue(targets.contains(board.getCellAt(0, 18)));
	}	

	// Tests of just walkways plus one door, 6 steps
	// These are Pink on the planning spreadsheet
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(24, 22, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCellAt(25, 17)));
		assertTrue(targets.contains(board.getCellAt(25, 19)));	
		assertTrue(targets.contains(board.getCellAt(19, 21)));	
		assertTrue(targets.contains(board.getCellAt(21, 21)));	
		assertTrue(targets.contains(board.getCellAt(23, 21)));	
		assertTrue(targets.contains(board.getCellAt(25, 21)));	
		assertTrue(targets.contains(board.getCellAt(18, 22)));		
		assertTrue(targets.contains(board.getCellAt(20, 22)));	
		assertTrue(targets.contains(board.getCellAt(22, 22)));
		// next assert tests a door space under the six steps
		assertTrue(targets.contains(board.getCellAt(23, 23)));	
		assertTrue(targets.contains(board.getCellAt(25, 23)));	
		assertTrue(targets.contains(board.getCellAt(25, 25)));
	}	

	// Test getting into a room 
	// These are Light Blue on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// first location
		board.calcTargets(12, 14, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		// door
		assertTrue(targets.contains(board.getCellAt(12, 15)));
		// walkways
		assertTrue(targets.contains(board.getCellAt(13, 14)));
		assertTrue(targets.contains(board.getCellAt(11, 14)));
		// second location
		board.calcTargets(17, 20, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		// door
		assertTrue(targets.contains(board.getCellAt(18, 20)));
		// walkways
		assertTrue(targets.contains(board.getCellAt(16, 20)));
		assertTrue(targets.contains(board.getCellAt(17, 19)));
		assertTrue(targets.contains(board.getCellAt(17, 21)));
	}

	// Test getting out of a room
	// These are Light Green on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(6, 12, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 12)));
		// Take two steps
		board.calcTargets(11, 21, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 20)));
		assertTrue(targets.contains(board.getCellAt(11, 19)));
		assertTrue(targets.contains(board.getCellAt(12, 20)));
	}

}
