package robo77.controller;

import java.util.List;
import java.util.function.Supplier;
import robo77.domain.RoboGame;
import robo77.domain.TurnResult;
import robo77.domain.card.submitstrategy.HumanSubmitStrategy;
import robo77.domain.player.Player;
import robo77.view.ConsoleInput;
import robo77.view.output.ConsoleOutput;

public class RoboGameController {

    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;

    public RoboGameController(ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
    }

    public void run() {
        RoboGame roboGame = initGame();
        playGame(roboGame);
    }

    private RoboGame initGame() {
        return retryOnInvalidInput(() -> {
            String playerName = consoleInput.readPlayerName();
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
            consoleOutput.showSumAndHandMessage(player.getHand());
            String cardValue = consoleInput.readCardToSubmit();
            return roboGame.playTurn(new HumanSubmitStrategy(cardValue));
        });
        consoleOutput.showSubmittedCard(player.getName(), result.submittedCard());
    }

    private void playBotTurns(RoboGame roboGame, Player player) {
        List<TurnResult> botResults = roboGame.playBotTurns();
        botResults.forEach(result -> consoleOutput.showSubmittedCard(player.getName(), result.submittedCard()));
    }

    private void showResult(RoboGame roboGame) {
        consoleOutput.showWinner(roboGame.getCurrentScore(), roboGame.getWinner().getName());
    }

    public <T> T retryOnInvalidInput(Supplier<T> input) {
        while (true) {
            try {
                return input.get();
            } catch (IllegalArgumentException e) {
                consoleOutput.showError(e.getMessage());
            }
        }
    }
}
