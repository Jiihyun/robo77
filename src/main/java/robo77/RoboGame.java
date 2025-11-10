package robo77;

import robo77.domain.Card;
import robo77.domain.Deck;
import robo77.domain.Referee;
import robo77.domain.player.Player;
import robo77.domain.turn.TurnManager;
import robo77.view.InputView;
import robo77.view.OutputView;

public class RoboGame {

    private final InputView inputView;
    private final OutputView outputView;

    public RoboGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Deck deck = new Deck();
        String playerName = inputView.readPlayerName();
        TurnManager turnManager = TurnManager.createTurn(playerName, deck);
        playGame(turnManager, deck);
    }

    private void playGame(TurnManager turnManager, Deck deck) {
        Referee referee = new Referee();
        Player currentPlayer = turnManager.getCurrentPlayer();
        while (!referee.shouldEndGame()) {
            Card newCard = deck.shareCard();
            Card submittedCard = getSubmittedCard(currentPlayer, newCard, referee.noticeScore());
            outputView.showSubmittedCard(currentPlayer.getName(), submittedCard);
            referee.recordScore(submittedCard.getValue());
            currentPlayer = turnManager.findNextTurnPlayer(submittedCard);
        }
        Player winner = referee.determineWinner(turnManager);
        outputView.showWinner(referee.noticeScore(), winner.getName());
    }

    private Card getSubmittedCard(Player currentPlayer, Card newCard, int score) {
        if (currentPlayer.isBot()) {
            return currentPlayer.submitCardByBot(newCard);
        }
        Card userChosenCard = readSubmitCard(currentPlayer, score);
        return currentPlayer.submitCard(userChosenCard, newCard);
    }

    private Card readSubmitCard(Player currentPlayer, int score) {
        outputView.showSumAndHandMessage(score, currentPlayer.getHand());
        String cardToSubmit = inputView.readCardToSubmit();
        return Card.from(cardToSubmit);
    }
}
