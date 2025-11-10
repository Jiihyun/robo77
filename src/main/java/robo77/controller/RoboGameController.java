package robo77.controller;

import java.util.function.Supplier;
import robo77.domain.RoboGame;
import robo77.domain.card.Card;
import robo77.domain.player.Player;
import robo77.view.InputView;
import robo77.view.OutputView;

public class RoboGameController {

    private final InputView inputView;
    private final OutputView outputView;

    public RoboGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        RoboGame roboGame = initGame();
        playGame(roboGame);
    }

    private RoboGame initGame() {
        return retryOnInvalidInput(() -> {
            String playerName = inputView.readPlayerName();
            return RoboGame.start(playerName);
        });
    }

    private void playGame(RoboGame roboGame) {
        while (roboGame.isPlaying()) {
            playTurn(roboGame);
        }
        showResult(roboGame);
    }

    private void playTurn(RoboGame roboGame) {
        Player currentPlayer = roboGame.getCurrentPlayer();
        Card submittedCard = getSubmittedCard(currentPlayer, roboGame.getCurrentScore());
        outputView.showSubmittedCard(currentPlayer.getName(), submittedCard);
        Card newCard = roboGame.drawCard();
        currentPlayer.pickCard(newCard);
        roboGame.processCard(submittedCard);
    }

    private Card getSubmittedCard(Player currentPlayer, int score) {
        if (currentPlayer.isBot()) {
            return currentPlayer.submitCardByBot();
        }
        return retryOnInvalidInput(() -> {
            outputView.showSumAndHandMessage(score, currentPlayer.getHand());
            String submittedCard = inputView.readCardToSubmit();
            return currentPlayer.submitCard(Card.from(submittedCard));
        });
    }

    private void showResult(RoboGame roboGame) {
        Player winner = roboGame.getWinner();
        outputView.showWinner(roboGame.getCurrentScore(), winner.getName());
    }

    public <T> T retryOnInvalidInput(Supplier<T> input) {
        while (true) {
            try {
                return input.get();
            } catch (IllegalArgumentException e) {
                outputView.showError(e.getMessage());
            }
        }
    }
}
