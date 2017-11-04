package clueGame;

public class Card {

	private String cardName;
	
	public boolean equals() {
		return false;
	}

	public Card(String cardName) {
		super();
		this.cardName = cardName;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
}
