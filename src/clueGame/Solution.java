/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the solution class that checks accusations
 */
package clueGame;

public class Solution {

	// create member variables
	private static Card person;
	private static Card room;
	private static Card weapon;

	// getters and setters
	public static Card getPerson() {
		return person;
	}
	public static void setPerson(Card p) {
		person = p;
	}
	public static Card getRoom() {
		return room;
	}
	public static void setRoom(Card r) {
		room = r;
	}
	public static Card getWeapon() {
		return weapon;
	}
	public static void setWeapon(Card w) {
		weapon = w;
	}
	
	// tests accusations from players
	public static boolean testAccusation(Card killer, Card weapon2, Card room2) {
		if(person.equals(killer) && weapon.equals(weapon2) && room.equals(room2)) {
			return true;
		}
		else {
			return false;
		}
	}
}
