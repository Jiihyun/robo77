package robo77.domain.card;

import java.util.ArrayList;
import java.util.List;

public final class CardGenerator {

    private CardGenerator() {
    }

    public static List<Card> createCards() {
        List<Card> cards = new ArrayList<>();
        createNormalCards(cards);
        createSpecialCards(cards);
        return cards;
    }

    private static void createNormalCards(List<Card> cards) {
        createZeroCard(cards);
        createTwoToNineCard(cards);
        createTenCard(cards);
        createMinusTenCard(cards);
        createMultipleOfElevenCard(cards);
        createSeventySixCard(cards);
    }

    private static void createSpecialCards(List<Card> cards) {
        createDoubleCard(cards);
        createReverseCard(cards);
    }

    private static void createZeroCard(List<Card> cards) {
        int cardValue = 0;
        int repeatCount = 4;
        addCards(cards, CardType.SUM, cardValue, repeatCount);
    }

    private static void createTwoToNineCard(List<Card> cards) {
        for (int repeat = 0; repeat < 3; repeat++) {
            for (int cardValue = 2; cardValue < 10; cardValue++) {
                cards.add(new Card(CardType.SUM, cardValue));
            }
        }
    }

    private static void createTenCard(List<Card> cards) {
        int cardValue = 10;
        int repeatCount = 8;
        addCards(cards, CardType.SUM, cardValue, repeatCount);
    }

    private static void createMinusTenCard(List<Card> cards) {
        int cardValue = -10;
        int repeatCount = 4;
        addCards(cards, CardType.SUM, cardValue, repeatCount);
    }

    private static void createMultipleOfElevenCard(List<Card> cards) {
        for (int cardValue = 11; cardValue < 77; cardValue += 11) {
            cards.add(new Card(CardType.SUM, cardValue));
        }
    }

    private static void createSeventySixCard(List<Card> cards) {
        int cardValue = 76;
        cards.add(new Card(CardType.SUM, cardValue));
    }

    private static void createDoubleCard(List<Card> cards) {
        int cardValue = 0;
        int repeatCount = 4;
        addCards(cards, CardType.DOUBLE, cardValue, repeatCount);
    }

    private static void createReverseCard(List<Card> cards) {
        int cardValue = 0;
        int repeatCount = 5;
        addCards(cards, CardType.REVERSE, cardValue, repeatCount);
    }

    private static void addCards(List<Card> cards, CardType type, int cardValue, int repeatCount) {
        for (int repeat = 0; repeat < repeatCount; repeat++) {
            cards.add(new Card(type, cardValue));
        }
    }
}
