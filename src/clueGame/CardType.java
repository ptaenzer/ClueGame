package clueGame;

public enum CardType {

	//Values for the type
	PERSON ("P"), WEAPON ("W"), ROOM ("R"), NONE ("");

	//declare the instance variable
	private String value;

	//Constructor for DoorDirection
	CardType (String aValue){
		value = aValue;
	}

	//Allows printing of this type
	public String toString(){
		return value;
	}

}
