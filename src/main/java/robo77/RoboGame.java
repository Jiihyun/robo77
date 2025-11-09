package robo77;

import robo77.domain.Card;
import robo77.domain.Deck;
import robo77.domain.Referee;
import robo77.domain.player.Player;
import robo77.domain.turn.TurnManager;
import robo77.domain.turn.TurnPolicy;
import robo77.domain.turn.TurnPolicyFactory;
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
            Card submittedCard = getSubmittedCard(currentPlayer, referee.noticeScore(), deck);
            outputView.showSubmittedCard(currentPlayer.getName(), submittedCard);
            referee.recordScore(submittedCard.getValue());

            TurnPolicy turnPolicy = TurnPolicyFactory.get(submittedCard.getCardType());
            currentPlayer = turnPolicy.nextTurnPlayer(turnManager);
        }
        Player winner = referee.determineWinner(turnManager);
        outputView.showWinner(referee.noticeScore(), winner.getName());
    }

    private Card getSubmittedCard(Player currentPlayer, int score, Deck deck) {
        Card newCard = deck.shareCard();
        if (currentPlayer.isBot()) {
            return currentPlayer.submitCardByBot(newCard);
        }
        outputView.showSumAndHandMessage(score, currentPlayer.getHand());
        String cardToSubmit = inputView.readCardToSubmit();
        Card submittedCard = Card.from(cardToSubmit);
        return currentPlayer.submitCard(submittedCard, newCard);
    }
}
