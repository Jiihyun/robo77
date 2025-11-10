package robo77;

import java.util.function.Supplier;
import robo77.domain.Card;
import robo77.domain.RoboGame;
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
        while (!roboGame.isGameOver()) {
            playTurn(roboGame);
        }
        showResult(roboGame);
    }

    private void playTurn(RoboGame roboGame) {
        Player currentPlayer = roboGame.getCurrentPlayer();
        Card newCard = roboGame.drawCard();
        Card submittedCard = getSubmittedCard(currentPlayer, newCard, roboGame.getCurrentScore());
        outputView.showSubmittedCard(currentPlayer.getName(), submittedCard);
        roboGame.processCard(submittedCard);
    }

    private Card getSubmittedCard(Player currentPlayer, Card newCard, int score) {
        if (currentPlayer.isBot()) {
            return currentPlayer.submitCardByBot(newCard);
        }
        return retryOnInvalidInput(() -> {
            outputView.showSumAndHandMessage(score, currentPlayer.getHand());
            String submittedCard = inputView.readCardToSubmit();
            return currentPlayer.submitCard(Card.from(submittedCard), newCard);
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
