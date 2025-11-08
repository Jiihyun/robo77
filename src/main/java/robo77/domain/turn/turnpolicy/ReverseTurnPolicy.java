package robo77.domain.turn.turnpolicy;

import robo77.domain.player.Player;
import robo77.domain.turn.TurnManager;
import robo77.domain.turn.TurnPolicy;

public class ReverseTurnPolicy implements TurnPolicy {

    private static final int SPECIAL_NUMBER_OF_PLAYERS = 2;

    @Override
    public Player nextTurnPlayer(TurnManager turnManager) {
        if (turnManager.getNumberOfPlayers() == SPECIAL_NUMBER_OF_PLAYERS) {
            return turnManager.getCurrentPlayer();
        }
        turnManager.reverseOrder();
        return turnManager.getCurrentPlayer();
    }
}
