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
		room = board.getCellAt(24, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(20, 18);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(15, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(4, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.CORNER, room.getDoorDirection());
		
		// Test cells that aren't doors
		room = board.getCellAt(3, 11);
		assertFalse(room.isDoorway());
		BoardCell cell = board.getCellAt(6, 0);
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
		assertEquals('E', board.getCellAt(0, 19).getInitial());
		assertEquals('B', board.getCellAt(13, 19).getInitial());
		// Test last cell in room
		assertEquals('O', board.getCellAt(6, 15).getInitial());
		assertEquals('K', board.getCellAt(25, 3).getInitial());
		// Test a walkway cell
		assertEquals('W', board.getCellAt(8, 8).getInitial());
		// Test a room cell
		assertEquals('F', board.getCellAt(16, 10).getInitial());
		// Test a closet cell
		assertEquals('C', board.getCellAt(11,12).getInitial());
	}

	// Ensure that player does not move around within room
	// This cell is Yellow on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		Set<BoardCell> testList = board.getAdjList(17, 21);
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
		assertTrue(testList.contains(board.getCellAt(23, 24)));
		//TEST DOORWAY UP
		testList = board.getAdjList(21, 10);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(20, 10)));
	}

	// Test adjacency at entrance to rooms
	// These tests are Orange in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(12, 7);
		assertTrue(testList.contains(board.getCellAt(11, 7)));
		assertTrue(testList.contains(board.getCellAt(13, 7)));
		assertTrue(testList.contains(board.getCellAt(12, 8)));
		assertTrue(testList.contains(board.getCellAt(12, 6)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(25, 4);
		assertTrue(testList.contains(board.getCellAt(25, 5)));
		assertTrue(testList.contains(board.getCellAt(24, 4)));
		assertEquals(2, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(0, 11);
		assertTrue(testList.contains(board.getCellAt(0, 10)));
		assertTrue(testList.contains(board.getCellAt(0, 12)));
		assertEquals(2, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(12, 21);
		assertTrue(testList.contains(board.getCellAt(12, 22)));
		assertTrue(testList.contains(board.getCellAt(12, 20)));
		assertTrue(testList.contains(board.getCellAt(11, 21)));
		assertTrue(testList.contains(board.getCellAt(13, 21)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are Green, Blue, White on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Green tests
		// Test on top edge of board
		Set<BoardCell> testList = board.getAdjList(0, 15);
		assertTrue(testList.contains(board.getCellAt(0, 14)));
		assertTrue(testList.contains(board.getCellAt(0, 16)));
		assertEquals(2, testList.size());

		//test to make sure suspicions that getAdj returns same thing every time... :,(
		/*testList = board.getAdjList(14, 12);
		assertEquals(3, testList.size());
		*/
		
		// Test on left edge of board
		testList = board.getAdjList(17, 0);
		assertTrue(testList.contains(board.getCellAt(16, 0)));
		assertTrue(testList.contains(board.getCellAt(17, 1)));
		assertTrue(testList.contains(board.getCellAt(18, 0)));
		assertEquals(3, testList.size());

		// Test on bottom edge of board
		testList = board.getAdjList(25, 14);
		assertTrue(testList.contains(board.getCellAt(24, 14)));
		assertTrue(testList.contains(board.getCellAt(25, 15)));
		assertEquals(2, testList.size());

		// Test on right edge of board
		testList = board.getAdjList(11, 25);
		assertTrue(testList.contains(board.getCellAt(10, 25)));
		assertTrue(testList.contains(board.getCellAt(11, 24)));
		assertTrue(testList.contains(board.getCellAt(12, 25)));
		assertEquals(3, testList.size());

		// Blue Tests
		// Tests next to rooms
		testList = board.getAdjList(6, 3);
		assertTrue(testList.contains(board.getCellAt(6, 2)));
		assertTrue(testList.contains(board.getCellAt(6, 4)));
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertEquals(3, testList.size());
		
		testList = board.getAdjList(19, 11);
		assertTrue(testList.contains(board.getCellAt(19, 10)));
		assertTrue(testList.contains(board.getCellAt(19, 12)));
		assertTrue(testList.contains(board.getCellAt(20, 11)));
		assertEquals(3, testList.size());
		
		// White test
		// Test surrounded by 4 walkways
		testList = board.getAdjList(20, 16);
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertTrue(testList.contains(board.getCellAt(20, 17)));
		assertTrue(testList.contains(board.getCellAt(19, 16)));
		assertTrue(testList.contains(board.getCellAt(21, 16)));
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
		board.calcTargets(11, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(10 , 15)));
		assertTrue(targets.contains(board.getCellAt(12, 15)));	
		assertTrue(targets.contains(board.getCellAt(10, 17)));
		assertTrue(targets.contains(board.getCellAt(12, 17)));	
		assertTrue(targets.contains(board.getCellAt(11, 18)));
		assertTrue(targets.contains(board.getCellAt(9, 16)));
		assertTrue(targets.contains(board.getCellAt(13, 16)));		
	}

	// Tests of just walkways, 4 steps
	// These are Pink on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(16, 2, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));
		assertTrue(targets.contains(board.getCellAt(15, 5)));
		assertTrue(targets.contains(board.getCellAt(16, 0)));
		assertTrue(targets.contains(board.getCellAt(16, 4)));
		assertTrue(targets.contains(board.getCellAt(16, 6)));
		assertTrue(targets.contains(board.getCellAt(17, 1)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		assertTrue(targets.contains(board.getCellAt(18, 0)));
	}	

	// Tests of just walkways plus one door, 6 steps
	// These are Pink on the planning spreadsheet
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(22, 24, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 25)));
		assertTrue(targets.contains(board.getCellAt(19, 25)));	
		assertTrue(targets.contains(board.getCellAt(21, 19)));	
		assertTrue(targets.contains(board.getCellAt(21, 21)));	
		assertTrue(targets.contains(board.getCellAt(21, 23)));	
		assertTrue(targets.contains(board.getCellAt(21, 25)));	
		assertTrue(targets.contains(board.getCellAt(22, 18)));		
		assertTrue(targets.contains(board.getCellAt(22, 20)));	
		assertTrue(targets.contains(board.getCellAt(22, 22)));
		// next assert tests a door space under the six steps
		assertTrue(targets.contains(board.getCellAt(23, 23)));	
		assertTrue(targets.contains(board.getCellAt(23, 25)));	
		assertTrue(targets.contains(board.getCellAt(25, 25)));
	}	

	// Test getting into a room 
	// These are Light Blue on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// first location
		board.calcTargets(14, 12, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		// door
		assertTrue(targets.contains(board.getCellAt(15, 12)));
		// walkways
		assertTrue(targets.contains(board.getCellAt(14, 13)));
		assertTrue(targets.contains(board.getCellAt(14, 11)));
		// second location
		board.calcTargets(20, 17, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		// door
		assertTrue(targets.contains(board.getCellAt(20, 18)));
		// walkways
		assertTrue(targets.contains(board.getCellAt(20, 16)));
		assertTrue(targets.contains(board.getCellAt(19, 17)));
		assertTrue(targets.contains(board.getCellAt(21, 17)));
	}

	// Test getting out of a room
	// These are Light Green on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(12, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 7)));
		// Take two steps
		board.calcTargets(21, 11, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 10)));
		assertTrue(targets.contains(board.getCellAt(19, 11)));
		assertTrue(targets.contains(board.getCellAt(20, 12)));
	}

}
