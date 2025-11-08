package robo77.domain.turn;

import robo77.domain.player.Player;

public interface TurnPolicy {

    Player nextTurnPlayer(TurnManager turnManager);
}
