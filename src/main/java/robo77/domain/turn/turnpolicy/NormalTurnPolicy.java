package robo77.domain.turn.turnpolicy;

import robo77.domain.player.Player;
import robo77.domain.player.Players;
import robo77.domain.turn.TurnPolicy;

public class NormalTurnPolicy implements TurnPolicy {

    @Override
    public Player findNextTurnPlayer(Players players) {
        Player current = players.pollFirst();
        players.pushToEnd(current);
        return players.peekFirst();
    }
}
