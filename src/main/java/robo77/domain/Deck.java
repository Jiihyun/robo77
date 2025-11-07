package robo77.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> cards;

    public Deck() {
        this.cards = createCards();
        shuffle();
    }

    private List<Card> createCards() {
        List<Card> deck = new ArrayList<>();
        createNormalCard(deck);
        createSpecialCard(deck);
        return deck;
    }

    private void createNormalCard(List<Card> deck) {
        createZeroCard(deck);
        createTwoToNineCard(deck);
        createTenCard(deck);
        createMinusTenCard(deck);
        createMultipleOfElevenCard(deck);
        createSeventySixCard(deck);
    }

    private void createSpecialCard(List<Card> deck) {
        createDoubleCard(deck);
        createReverseCard(deck);
    }


    private void createZeroCard(List<Card> deck) {
        for (int repeat = 0; repeat < 4; repeat++) {
            deck.add(new Card(CardType.SUM, 0));
        }
    }

    private void createTwoToNineCard(List<Card> deck) {
        for (int repeat = 0; repeat < 3; repeat++) {
            for (int number = 2; number < 10; number++) {
                deck.add(new Card(CardType.SUM, number));
            }
        }
    }

    private void createTenCard(List<Card> deck) {
        for (int repeat = 0; repeat < 8; repeat++) {
            deck.add(new Card(CardType.SUM, 10));
        }
    }

    private void createMinusTenCard(List<Card> deck) {
        for (int repeat = 0; repeat < 4; repeat++) {
            deck.add(new Card(CardType.SUM, -10));
        }
    }

    private void createMultipleOfElevenCard(List<Card> deck) {
        for (int number = 11; number < 77; number += 11) {
            deck.add(new Card(CardType.SUM, number));
        }
    }

    private void createSeventySixCard(List<Card> deck) {
        deck.add(new Card(CardType.SUM, 76));
    }

    private void createDoubleCard(List<Card> deck) {
        for (int repeat = 0; repeat < 4; repeat++) {
            deck.add(new Card(CardType.DOUBLE, 0));
        }
    }

    private void createReverseCard(List<Card> deck) {
        for (int repeat = 0; repeat < 5; repeat++) {
            deck.add(new Card(CardType.REVERSE, 0));
        }
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Hand> shareCards() {
        List<Hand> hands = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Card> hand = new ArrayList<>(cards.subList(0, 5));
            cards.subList(0, 5).clear();
            hands.add(new Hand(hand));
        }
        return hands;
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }
}
