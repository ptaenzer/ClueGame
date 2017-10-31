/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is our custom exception class thrown when the configuration isn't correct
 */

package clueGame;

public class BadConfigException extends Exception {
	
	//Generic constructor
	public BadConfigException() {
		super("Error: BadConfigException");
	}
	
	//Constructor with parameter
	public BadConfigException(String s) {
		super(s + "has a BadConfigException");
	}

	//Override for printing of this error
	@Override
	public String toString() {
		return "BadConfigException";
	}
	
}
