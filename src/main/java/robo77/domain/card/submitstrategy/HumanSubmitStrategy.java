package robo77.domain.card.submitstrategy;

import robo77.domain.card.Card;
import robo77.domain.card.SubmitCardStrategy;
import robo77.domain.player.Player;

public class HumanSubmitStrategy implements SubmitCardStrategy {

    private final String value;

    public HumanSubmitStrategy(String value) {
        this.value = value;
    }

    @Override
    public Card submit(Player player) {
        return player.submitCard(Card.from(value));
    }
}
