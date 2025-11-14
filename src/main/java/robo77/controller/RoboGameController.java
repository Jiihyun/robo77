package robo77.controller;

import java.util.List;
import java.util.function.Supplier;
import robo77.domain.RoboGame;
import robo77.domain.TurnResult;
import robo77.domain.card.submitstrategy.HumanSubmitStrategy;
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

    private void playGame(RoboGame game) {
        while (game.isPlaying()) {
            playTurn(game);
        }
        showResult(game);
    }

    private void playTurn(RoboGame game) {
        Player player = game.getCurrentPlayer();
        if (player.isBot()) {
            playBotTurns(game, player);
            return;
        }
        playPlayerTurn(game, player);
    }

    private void playPlayerTurn(RoboGame roboGame, Player player) {
        TurnResult result = retryOnInvalidInput(() -> {
            outputView.showSumAndHandMessage(player.getHand());
            String cardValue = inputView.readCardToSubmit();
            return roboGame.playTurn(new HumanSubmitStrategy(cardValue));
        });
        outputView.showSubmittedCard(player.getName(), result.submittedCard());
    }

    private void playBotTurns(RoboGame roboGame, Player player) {
        List<TurnResult> botResults = roboGame.playBotTurns();
        botResults.forEach(result -> outputView.showSubmittedCard(player.getName(), result.submittedCard()));
    }

    private void showResult(RoboGame roboGame) {
        outputView.showWinner(roboGame.getCurrentScore(), roboGame.getWinner().getName());
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
