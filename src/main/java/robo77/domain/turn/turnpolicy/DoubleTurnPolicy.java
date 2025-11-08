package robo77.domain.turn.turnpolicy;

import robo77.domain.player.Player;
import robo77.domain.turn.TurnManager;
import robo77.domain.turn.TurnPolicy;

public class DoubleTurnPolicy implements TurnPolicy {

    @Override
    public Player nextTurnPlayer(TurnManager turnManager) {
        Player current = turnManager.pollCurrentPlayer();
        Player nextPlayer = turnManager.getCurrentPlayer();

        turnManager.pushToFront(nextPlayer);
        turnManager.pushToEnd(current);
        return nextPlayer;
    }
}
