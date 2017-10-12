/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the test class that contains test methods for our current methods in the board class.
 */
package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

/*
 * Part 1:
 * Created all tests and adjusted squares for each test to create seperate cases,
 * all tests fail, ready to move on to Part 2.
 */

public class IntBoardTests {
	private Map<BoardCell, Set<BoardCell>> b;
	private IntBoard board;

	@Before public void setUpIntBoard() {
		b = new HashMap<BoardCell, Set<BoardCell>>();
		board = new IntBoard(b);
	}
	
	/*
	 * Test adjacencies for top left corner
	 */
	@Test
	public void testAdjacency0()
	{
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for bottom right corner 
	 */
	@Test
	public void testAdjacency1()
	{
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for a right edge
	 */
	@Test
	public void testAdjacency2()
	{
		BoardCell cell = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test adjacencies for a left edge
	 */
	@Test
	public void testAdjacency3()
	{
		BoardCell cell = board.getCell(1,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test adjacencies for second column, middle of grid
	 */
	@Test
	public void testAdjacency4()
	{
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * Test adjacencies for second column from the last, middle of grid
	 */
	@Test
	public void testAdjacency5()
	{
		BoardCell cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets0_1()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	public void testTargets0_2()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
	}
	
	@Test
	public void testTargets0_3()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	public void testTargets1_4()
	{
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 4);
		Set targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	@Test
	public void testTargets1_5()
	{
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 5);
		Set targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	
	@Test
	public void testTargets1_6()
	{
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 6);
		Set targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}

}