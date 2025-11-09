package robo77.domain;

import robo77.domain.player.Player;
import robo77.domain.turn.TurnManager;

public class Referee {

    private final GameScore gameScore;

    public Referee() {
        this.gameScore = GameScore.createWithEndCondition();
    }

    public boolean shouldEndGame() {
        return gameScore.isOverLimit();
    }

    public void recordScore(int cardValue) {
        gameScore.add(cardValue);
    }

    public Player determineWinner(TurnManager turnManager) {
        return turnManager.getPlayers()
                .getFirst();
    }

    public int noticeScore() {
        return gameScore.getValue();
    }
}
