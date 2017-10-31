/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is our enumerated type class for the door directions
 */

package clueGame;

public enum DoorDirection {
	//Values for the type
	UP ("U"), DOWN ("D"), LEFT ("L"), RIGHT ("R"), CORNER ("C"), NONE ("");
	
	//declare the instance variable
	private String value;
	
	//Constructor for DoorDirection
	DoorDirection (String aValue){
		value = aValue;
	}
	
	//Allows printing of this type
	public String toString(){
		return value;
	}
}
