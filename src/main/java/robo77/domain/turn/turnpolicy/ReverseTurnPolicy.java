package robo77.domain.turn.turnpolicy;

import robo77.domain.player.Player;
import robo77.domain.player.Players;
import robo77.domain.turn.TurnPolicy;

public class ReverseTurnPolicy implements TurnPolicy {

    private static final int SPECIAL_NUMBER_OF_PLAYERS = 2;

    @Override
    public Player findNextTurnPlayer(Players players) {
        removeDuplicatedHeadAndTail(players);
        if (players.getUniquePlayerCount() == SPECIAL_NUMBER_OF_PLAYERS) {
            return players.peekFirst();
        }
        players.reverseOrder();
        return players.peekFirst();
    }

    private void removeDuplicatedHeadAndTail(Players players) {
        Player first = players.peekFirst();
        Player last = players.peekLast();
        if (first != null && first.equals(last)) {
            players.pollLast();
        }
    }
}
