package robo77.domain.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import robo77.domain.Hand;
import robo77.exception.ExceptionMessage;

public class Deck {

    private final List<Card> cards;

    public Deck() {
        this.cards = CardGenerator.createCards();
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public Hand drawCards() {
        List<Card> hand = new ArrayList<>(cards.subList(0, 5));
        cards.subList(0, 5).clear();
        return new Hand(hand);
    }

    public Card drawCard() {
        Card card = cards.removeFirst();
        if (card == null) {
            throw new IllegalStateException(ExceptionMessage.CARD_NOT_EXISTS.getMessage());
        }
        return card;
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }
}
