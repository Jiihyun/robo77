package robo77.domain.card.submitstrategy;

import robo77.domain.card.Card;
import robo77.domain.card.SubmitCardStrategy;
import robo77.domain.player.Player;

public class BotSubmitStrategy implements SubmitCardStrategy {

    @Override
    public Card submit(Player player) {
        return player.submitCardByBot();
    }
}
