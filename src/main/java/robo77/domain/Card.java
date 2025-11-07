package robo77.domain;

import java.util.Objects;

public class Card {

    private final CardType cardType;
    private final int value;

    public Card(CardType cardType, int value) {
        this.cardType = cardType;
        this.value = value;
    }

    public static Card from(String cardToSubmit) {
        try {
            int value = Integer.parseInt(cardToSubmit);
            return new Card(CardType.SUM, value);
        } catch (NumberFormatException numberFormatException) {
            return new Card(CardType.from(cardToSubmit), -1);
        }
    }

    public CardType getCardType() {
        return cardType;
    }

    public int getValue() {
        return value;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Card card)) {
            return false;
        }

        return getValue() == card.getValue() && getCardType() == card.getCardType();
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getCardType());
        result = 31 * result + getValue();
        return result;
    }
}
