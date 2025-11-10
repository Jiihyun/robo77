package robo77.domain;

import robo77.domain.card.Card;
import robo77.domain.card.Deck;
import robo77.domain.player.Player;
import robo77.domain.turn.TurnManager;

public class RoboGame {

    private final Deck deck;
    private final TurnManager turnManager;
    private final Referee referee;

    public RoboGame(Deck deck, TurnManager turnManager, Referee referee) {
        this.deck = deck;
        this.turnManager = turnManager;
        this.referee = referee;
    }

    public static RoboGame start(String playerName) {
        Deck deck = new Deck();
        TurnManager turnManager = TurnManager.createTurn(playerName, deck);
        Referee referee = new Referee();
        return new RoboGame(deck, turnManager, referee);
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    public int getCurrentScore() {
        return referee.noticeScore();
    }

    public boolean isGameOver() {
        return referee.shouldEndGame();
    }

    public Card drawCard() {
        return deck.drawCard();
    }

    public void processCard(Card submittedCard) {
        referee.recordScore(submittedCard.getValue());
        turnManager.findNextTurnPlayer(submittedCard);
    }

    public Player getWinner() {
        return referee.determineWinner(turnManager);
    }
}
