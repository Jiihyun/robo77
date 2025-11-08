package robo77.domain.turn.turnpolicy;

import robo77.domain.player.Player;
import robo77.domain.turn.TurnManager;
import robo77.domain.turn.TurnPolicy;

public class NormalTurnPolicy implements TurnPolicy {

    @Override
    public Player nextTurnPlayer(TurnManager turnManager) {
        Player current = turnManager.pollCurrentPlayer();
        turnManager.pushToEnd(current);
        return turnManager.getCurrentPlayer();
    }
}
