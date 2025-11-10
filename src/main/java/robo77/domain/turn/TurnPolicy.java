package robo77.domain.turn;

import robo77.domain.player.Player;
import robo77.domain.player.Players;

public interface TurnPolicy {

    Player findNextTurnPlayer(Players players);
}
