package clueGame;

public class Solution {

	private static Card person;
	private static Card room;
	private static Card weapon;

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
	public static boolean testAccusation(Card killer, Card weapon2, Card room2) {
		if(person == killer && weapon == weapon2 && room == room2) {
			return true;
		}
		else {
			return false;
		}
	}
}
