package robo77.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import robo77.domain.RoboGame;
import robo77.domain.card.Card;
import robo77.domain.player.Player;

public class GameSessionManager {

    private final Map<String, RoboGame> games = new ConcurrentHashMap<>();

    public RoboGame startGame(String channelId, String playerName) {
        RoboGame game = RoboGame.start(playerName);
        games.put(channelId, game);
        return game;
    }

    public RoboGame findExistingGame(String channelId) {
        return games.get(channelId);
    }

    public void endGame(String channelId) {
        games.remove(channelId);
    }

    public TurnResult playTurn(String channelId, String cardValue) {
        RoboGame roboGame = findExistingGame(channelId);
        if (roboGame == null) {
            throw new IllegalStateException("⚠️ 진행 중인 게임이 없습니다.");
        }
        if (!roboGame.isPlaying()) {
            throw new IllegalStateException("⚠️ 게임이 이미 종료되었습니다.");
        }

        Player currentPlayer = roboGame.getCurrentPlayer();
        Card submittedCard = getSubmittedCard(currentPlayer, cardValue);

        Card newCard = roboGame.drawCard();
        currentPlayer.pickCard(newCard);
        roboGame.processCard(submittedCard);

        boolean isGameOver = !roboGame.isPlaying();
        Player winner = isGameOver ? roboGame.getWinner() : null;

        return new TurnResult(currentPlayer, submittedCard, newCard, isGameOver, winner);
    }

    private Card getSubmittedCard(Player currentPlayer, String cardValue) {
        if (currentPlayer.isBot()) {
            return currentPlayer.submitCardByBot();
        }
        return currentPlayer.submitCard(Card.from(cardValue));
    }
}
