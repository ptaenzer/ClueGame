package clueGame;

public class Card {

	private String cardName;
	private CardType cardType;
	
	public boolean equals(Card card) {
		if(cardName == card.getCardName() && cardType == card.getCardType()) {
			return true;
		}
		return false;
	}

	public Card(String name, CardType type) {
		super();
		this.cardName = name;
		this.cardType = type;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	
}
