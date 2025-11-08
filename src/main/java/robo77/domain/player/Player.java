package robo77.domain.player;

import robo77.domain.Card;
import robo77.domain.Hand;

public class Player {

    private final Name name;

    private final Hand hand;

    public Player(String name, Hand hand) {
        this.name = new Name(name);
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean hasSubmittedCard(Card submittedCard) {
        return hand.getHoldingCards().stream()
                .anyMatch(card -> card.equals(submittedCard));
    }

    //TODO: x2 대비 해야함
    public void submitCard(Card submittedCard, Card newCard) {
        hand.removeCard(submittedCard);
        hand.addCard(newCard);
    }

    public Card submitCardByBot(Card newCard) {
        Card card = hand.removeCardByBot();
        hand.addCard(newCard);
        return card;
    }

    public String getName() {
        return name.getValue();
    }
}
