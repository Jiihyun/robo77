package robo77.domain.player;

import robo77.domain.Card;
import robo77.domain.Hand;
import robo77.exception.ExceptionMessage;

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

    public Card submitCard(Card submittedCard, Card newCard) {
        validateSubmittableCard(submittedCard);
        hand.removeCard(submittedCard);
        hand.addCard(newCard);
        return submittedCard;
    }

    private void validateSubmittableCard(Card submittedCard) {
        if (cannotSubmit(submittedCard)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_CARD.getMessage());
        }
    }

    private boolean cannotSubmit(Card submittedCard) {
        return hand.hasCard(submittedCard);
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
