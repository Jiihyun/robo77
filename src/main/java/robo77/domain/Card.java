package robo77.domain;

public class Card {

    private final CardType cardType;
    private final int value;

    public Card(CardType cardType, int value) {
        this.cardType = cardType;
        this.value = value;
    }

    public CardType getCardType() {
        return cardType;
    }

    public int getValue() {
        return value;
    }
}
