package clueGame;

public class BadConfigException extends Exception {
	
	public BadConfigException() {
		super("Error: BadConfigException");
	}

	@Override
	public String toString() {
		return "BadConfigException";
	}
	
}
