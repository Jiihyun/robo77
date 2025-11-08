package robo77.domain;

import java.util.List;
import robo77.exception.ExceptionMessage;

public class Hand {

    private static final int HOLDING_CARD_SIZE = 5;

    private final List<Card> holdingCards;

    public Hand(List<Card> holdingCards) {
        validateCardSize(holdingCards);
        this.holdingCards = holdingCards;
    }

    private void validateCardSize(List<Card> holdingCards) {
        if (holdingCards.size() != HOLDING_CARD_SIZE) {
            throw new IllegalArgumentException(ExceptionMessage.HOLDING_CARD_OUT_OF_RANGE.getMessage());
        }
    }

    public void removeCard(Card submittedCard) {
        holdingCards.remove(submittedCard);
    }

    public Card removeCardByBot() {
        return holdingCards.removeFirst();
    }

    public void addCard(Card newCard) {
        holdingCards.add(newCard);
    }

    public List<Card> getHoldingCards() {
        return holdingCards;
    }
}
