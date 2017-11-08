package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Field;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	// Constants that will be used in multiple methods
	public static final int LEGEND_SIZE = 9;
	public static final int PLAYER_SIZE = 10;
	public static final int WEAPON_SIZE = 14;
	public static final int DECK_SIZE = LEGEND_SIZE + PLAYER_SIZE + WEAPON_SIZE;
	public static final int NUM_CARD = (DECK_SIZE-3)/PLAYER_SIZE;
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

	// Tests selecting a random target location from all walkways
	@Test
	public void testSelectRandomTarget() {
		ComputerPlayer player = new ComputerPlayer("Greeny", Color.GREEN, false);
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(7, 12, 2);
		boolean loc_7_10 = false;
		boolean loc_7_14 = false;
		boolean loc_8_11 = false;
		boolean loc_8_13 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(7, 10)) {
				loc_7_10 = true;
			}
			else if (selected == board.getCellAt(7, 14)) {
				loc_7_14 = true;
			}
			else if (selected ==board.getCellAt(8, 11)) {
				loc_8_11 = true;
			}
			else if (selected ==board.getCellAt(8, 13)) {
				loc_8_13 = true;
			}
			else {
				fail("Invalid target selected");
			}
		}
		// Ensure each target was selected at least once
		assertTrue(loc_7_10);
		assertTrue(loc_7_14);
		assertTrue(loc_8_11);
		assertTrue(loc_8_13);
	}

	// Tests selecting a room target location from list as long as not just visited
	@Test
	public void testSelectTarget() {
		ComputerPlayer player = new ComputerPlayer("Greeny", Color.GREEN, false);
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(0, 16, 2);
		boolean loc_0_14 = false;
		boolean loc_0_18 = false;
		boolean loc_1_17 = false;
		boolean loc_2_16 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(0, 14)) {
				loc_0_14 = true;
			}
			else if (selected == board.getCellAt(0, 18)) {
				loc_0_18 = true;
			}
			else if (selected ==board.getCellAt(1, 17)) {
				loc_1_17 = true;
			}
			else if (selected ==board.getCellAt(2, 16)) {
				loc_2_16 = true;
			}
			else {
				fail("Invalid target selected");
			}
		}
		// Ensure each target was selected at least once
		assertTrue(loc_0_14);
		assertFalse(loc_0_18);
		assertFalse(loc_1_17);
		assertFalse(loc_2_16);
	}

	// Tests not selecting a room target location from list with a just visited
	@Test
	public void testSelectTarget2() {
		ComputerPlayer player = new ComputerPlayer("Greeny", Color.GREEN, false);
		player.setJustVisited('O');
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(0, 16, 2);
		boolean loc_0_14 = false;
		boolean loc_0_18 = false;
		boolean loc_1_17 = false;
		boolean loc_2_16 = false;
		// Run the test a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(0, 14)) {
				loc_0_14 = true;
			}
			else if (selected == board.getCellAt(0, 18)) {
				loc_0_18 = true;
			}
			else if (selected ==board.getCellAt(1, 17)) {
				loc_1_17 = true;
			}
			else if (selected ==board.getCellAt(2, 16)) {
				loc_2_16 = true;
			}
			else {
				fail("Invalid target selected");
			}
		}
		// Ensure each target was selected at least once
		assertTrue(loc_0_14);
		assertTrue(loc_0_18);
		assertTrue(loc_1_17);
		assertTrue(loc_2_16);
	}

	// Tests correct and not correct accusations 
	@Test
	public void testAccusation() {
		// correct accusation
		Card killer = Solution.getPerson();
		Card weapon = Solution.getWeapon();
		Card room = Solution.getRoom();
		assertTrue(Solution.testAccusation(killer,weapon,room));
		// not correct accusation
		killer = null;
		weapon = Solution.getWeapon();
		room = Solution.getRoom();
		assertFalse(Solution.testAccusation(killer,weapon,room));
		killer = Solution.getPerson();
		weapon = null;
		room = Solution.getRoom();
		assertFalse(Solution.testAccusation(killer,weapon,room));
		killer = Solution.getPerson();
		weapon = Solution.getWeapon();
		room = null;
		assertFalse(Solution.testAccusation(killer,weapon,room));
	}


}
