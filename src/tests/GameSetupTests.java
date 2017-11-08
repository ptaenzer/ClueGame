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
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class GameSetupTests {
	// Constants that will be used in multiple methods
	public static final int LEGEND_SIZE = 9;
	public static final int PLAYER_SIZE = 10;
	public static final int WEAPON_SIZE = 14;
	public static final int DECK_SIZE = LEGEND_SIZE + PLAYER_SIZE + WEAPON_SIZE;
	public static final int NUM_CARD = DECK_SIZE/PLAYER_SIZE;
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

	//tests player creation
	@Test
	public void testPlayers() {
		Map<String, Player> players = board.getPlayers();
		// Test for correct number of players
		assertEquals(PLAYER_SIZE, players.size());
		// Test players to make sure the files were imported correctly
		assertEquals(Color.GREEN, players.get("Greeny").getColor());
		assertEquals(Color.YELLOW, players.get("General Custard").getColor());
		assertEquals(Color.PINK, players.get("Princess Peach").getColor());
		assertEquals(Color.GRAY, players.get("Hariette A. Ness").getColor());
		assertEquals(Color.CYAN, players.get("Padme Amidala").getColor());
		// tests if Greeny is human and if other is not human
		assertEquals(true, players.get("Greeny").isHuman());
		assertEquals(false, players.get("Padme Amidala").isHuman());
	}


	//tests Deck creation
	@Test
	public void testDeck() {
		Map<CardType, ArrayList<Card>> deck = board.getDeck();
		// Test for correct number of cards
		assertEquals(DECK_SIZE, deck.get(CardType.ROOM).size() + deck.get(CardType.PERSON).size() + deck.get(CardType.WEAPON).size());
		// Tests for correct number of each type of card
		assertEquals(LEGEND_SIZE, deck.get(CardType.ROOM).size());
		assertEquals(PLAYER_SIZE, deck.get(CardType.PERSON).size());
		assertEquals(WEAPON_SIZE, deck.get(CardType.WEAPON).size());
		// Tests for one value in each type of card
		assertEquals(deck.get(CardType.ROOM).get(0).getCardName(), "Gazibo");
		assertEquals(deck.get(CardType.PERSON).get(0).getCardName(), "Jack the Ripper");
		assertEquals(deck.get(CardType.WEAPON).get(0).getCardName(), "Freddy Kruger's Claws");
	}

	//tests dealing of cards
	@Test
	public void testDeal() {
		Map<String, Player> players = board.getPlayers();
		// Test for correct number of cards in player
		assertEquals(NUM_CARD, players.get("Greeny").getCards().size());
		assertEquals(NUM_CARD, players.get("Mike Hawk").getCards().size());
		assertEquals(NUM_CARD, players.get("Padme Amidala").getCards().size());
		// Test for duplicate Cards
		for(String firstP : players.keySet()) {
			for(String secondP : players.keySet()) {
				if(!firstP.equalsIgnoreCase(secondP)) {
					for(int k = 0; k < NUM_CARD; k++) {
						for(int l = 0; l < NUM_CARD; l++) {
							assertNotSame(players.get(firstP).getCards().get(k), players.get(secondP).getCards().get(l));
							
						}
					}
				}
			}
		}
	}

}
