package robo77.domain.card;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import robo77.domain.Hand;
import robo77.exception.ExceptionMessage;

public class Deck {

    public static final int DEFAULT_HAND_SIZE = 5;

    private final List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public Hand drawCards() {
        List<Card> hand = IntStream.range(0, DEFAULT_HAND_SIZE)
                .mapToObj(repeatCount -> drawCard())
                .toList();
        return new Hand(hand);
    }

    public Card drawCard() {
        try {
            return cards.removeFirst();
        } catch (NoSuchElementException noSuchElementException) {
            throw new IllegalStateException(ExceptionMessage.CARD_NOT_EXISTS.getMessage());
        }
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }
}
