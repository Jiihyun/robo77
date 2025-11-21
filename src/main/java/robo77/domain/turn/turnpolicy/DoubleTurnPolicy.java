package robo77.domain.turn.turnpolicy;

import robo77.domain.player.Player;
import robo77.domain.player.Players;
import robo77.domain.turn.TurnPolicy;

public class DoubleTurnPolicy implements TurnPolicy {

    @Override
    public Player findNextTurnPlayer(Players players) {
        removeDuplicatedHeadAndTail(players);
        Player current = players.pollFirst();
        Player nextPlayer = players.peekFirst();
        players.pushToFront(nextPlayer);
        players.pushToEnd(current);
        return nextPlayer;
    }

    private void removeDuplicatedHeadAndTail(Players players) {
        Player first = players.peekFirst();
        Player last = players.peekLast();
        if (first != null && first.equals(last)) {
            players.pollLast();
        }
    }
}
