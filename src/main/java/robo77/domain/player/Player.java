package robo77.domain.player;

import robo77.domain.Card;
import robo77.domain.Hand;

public class Player {

    private static final String BOT = "bot";

    private final Name name;
    private final Hand hand;

    public Player(String name, Hand hand) {
        this.name = new Name(name);
        this.hand = hand;
    }

    public static Player byBot(Hand hand) {
        return new Player(BOT, hand);
    }

    public boolean hasSubmittedCard(Card submittedCard) {
        return hand.getHoldingCards().stream()
                .anyMatch(card -> card.equals(submittedCard));
    }

    public Card submitCard(Card submittedCard, Card newCard) {
        hand.removeCard(submittedCard);
        hand.addCard(newCard);
        return submittedCard;
    }

    public Card submitCardByBot(Card newCard) {
        Card submittedCard = hand.getFirstCard();
        hand.removeCard(submittedCard);
        hand.addCard(newCard);
        return submittedCard;
    }

    public boolean isBot() {
        return name.isSame(BOT);
    }

    public String getName() {
        return name.getValue();
    }

    public Hand getHand() {
        return hand;
    }
}
