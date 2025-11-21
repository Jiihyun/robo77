package robo77.discord;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import robo77.domain.RoboGame;

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
}
