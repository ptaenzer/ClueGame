package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
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

	// Tests creation of a suggestion
	@Test
	public void testCreateSuggestion() {
		ComputerPlayer player = new ComputerPlayer("Greeny", Color.GREEN, false);
		player.setJustVisited('O');
		player.addSeenCard(player.getJustVisitedCard());
		player.addSeenCard(new Card("Greeny", CardType.PERSON));
		player.addSeenCard(new Card("General Custard", CardType.PERSON));
		player.addSeenCard(new Card("Napolean Dynamite", CardType.PERSON));
		player.addSeenCard(new Card("Princess Peach", CardType.PERSON));
		player.addSeenCard(new Card("Harpoon Gun", CardType.WEAPON));
		player.addSeenCard(new Card("Noisy Cricket", CardType.WEAPON));
		player.addSeenCard(new Card("Holy Grail", CardType.WEAPON));
		player.addSeenCard(new Card("DL-44 Blaster Pistol", CardType.WEAPON));
		player.addSeenCard(new Card("Exploding Bubble Gum", CardType.WEAPON));
		player.addSeenCard(new Card("Freddy Kruger's Claws", CardType.WEAPON));
		player.addSeenCard(new Card("Slap Bet", CardType.WEAPON));
		// create booleans for tests and set solution
		boolean array1 = false;
		boolean array2 = false;
		boolean array3 = false;
		boolean array4 = false;
		Random rand = new Random();
		int r1 = rand.nextInt(Board.getDeck().get(CardType.PERSON).size());
		while(player.getSeen().contains(Board.getDeck().get(CardType.PERSON).get(r1))) {
			r1 = rand.nextInt(Board.getDeck().get(CardType.PERSON).size());
		}
		int r2 = rand.nextInt(Board.getDeck().get(CardType.PERSON).size());
		while(player.getSeen().contains(Board.getDeck().get(CardType.PERSON).get(r2))) {
			r2 = rand.nextInt(Board.getDeck().get(CardType.PERSON).size());
		}
		int r3 = rand.nextInt(Board.getDeck().get(CardType.PERSON).size());
		while(player.getSeen().contains(Board.getDeck().get(CardType.PERSON).get(r3))) {
			r3 = rand.nextInt(Board.getDeck().get(CardType.PERSON).size());
		}
		int r4 = rand.nextInt(Board.getDeck().get(CardType.PERSON).size());
		while(player.getSeen().contains(Board.getDeck().get(CardType.PERSON).get(r4))) {
			r4 = rand.nextInt(Board.getDeck().get(CardType.PERSON).size());
		}
		int r5 = rand.nextInt(Board.getDeck().get(CardType.WEAPON).size());
		while(player.getSeen().contains(Board.getDeck().get(CardType.WEAPON).get(r5))) {
			r5 = rand.nextInt(Board.getDeck().get(CardType.WEAPON).size());
		}
		int r6 = rand.nextInt(Board.getDeck().get(CardType.WEAPON).size());
		while(player.getSeen().contains(Board.getDeck().get(CardType.WEAPON).get(r6))) {
			r6 = rand.nextInt(Board.getDeck().get(CardType.WEAPON).size());
		}
		int r7 = rand.nextInt(Board.getDeck().get(CardType.WEAPON).size());
		while(player.getSeen().contains(Board.getDeck().get(CardType.WEAPON).get(r7))) {
			r7 = rand.nextInt(Board.getDeck().get(CardType.WEAPON).size());
		}
		int r8 = rand.nextInt(Board.getDeck().get(CardType.WEAPON).size());
		while(player.getSeen().contains(Board.getDeck().get(CardType.WEAPON).get(r8))) {
			r8 = rand.nextInt(Board.getDeck().get(CardType.WEAPON).size());
		}
		ArrayList<Card> sug1 = new ArrayList<>(Arrays.asList(Board.getDeck().get(CardType.PERSON).get(r1), Board.getDeck().get(CardType.WEAPON).get(r5), player.getJustVisitedCard()));
		ArrayList<Card> sug2 = new ArrayList<>(Arrays.asList(Board.getDeck().get(CardType.PERSON).get(r2), Board.getDeck().get(CardType.WEAPON).get(r6), player.getJustVisitedCard()));
		ArrayList<Card> sug3 = new ArrayList<>(Arrays.asList(Board.getDeck().get(CardType.PERSON).get(r3), Board.getDeck().get(CardType.WEAPON).get(r7), player.getJustVisitedCard()));
		ArrayList<Card> sug4 = new ArrayList<>(Arrays.asList(Board.getDeck().get(CardType.PERSON).get(r4), Board.getDeck().get(CardType.WEAPON).get(r8), player.getJustVisitedCard()));
		// Run the test a large number of times
		for (int i=0; i<10000000; i++) {
			ArrayList<Card> sug = player.createSuggestion(player.getJustVisitedCard());
			if (sug.get(0).equals(sug1.get(0)) && sug.get(1).equals(sug1.get(1)) && sug.get(2).equals(sug1.get(2))) {
				array1 = true;
			}
			if (sug.get(0).equals(sug2.get(0)) && sug.get(1).equals(sug2.get(1)) && sug.get(2).equals(sug2.get(2))) {
				array2 = true;
			}
			if (sug.get(0).equals(sug3.get(0)) && sug.get(1).equals(sug3.get(1)) && sug.get(2).equals(sug3.get(2))) {
				array3 = true;
			}
			if (sug.get(0).equals(sug4.get(0)) && sug.get(1).equals(sug4.get(1)) && sug.get(2).equals(sug4.get(2))) {
				array4 = true;
			}
		}
		// Ensure each target was selected at least once
		assertTrue(array1);
		assertTrue(array2);
		assertTrue(array3);
		assertTrue(array4);
	}


}
