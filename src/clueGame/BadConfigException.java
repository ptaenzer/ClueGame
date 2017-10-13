package clueGame;

public class BadConfigException extends Exception {
	
	public BadConfigException() {
		super("Error: BadConfigException");
	}
	public BadConfigException(String s) {
		super(s + "has a BadConfigException");
	}

	@Override
	public String toString() {
		return "BadConfigException";
	}
	
}
