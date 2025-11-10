package robo77.domain.turn;

import java.util.HashMap;
import java.util.Map;
import robo77.domain.card.CardType;
import robo77.domain.turn.turnpolicy.DoubleTurnPolicy;
import robo77.domain.turn.turnpolicy.NormalTurnPolicy;
import robo77.domain.turn.turnpolicy.ReverseTurnPolicy;

public class TurnPolicyFactory {

    private static final Map<CardType, TurnPolicy> strategies = new HashMap<>();

    static {
        strategies.put(CardType.SUM, new NormalTurnPolicy());
        strategies.put(CardType.REVERSE, new ReverseTurnPolicy());
        strategies.put(CardType.DOUBLE, new DoubleTurnPolicy());
    }

    public static TurnPolicy get(CardType type) {
        return strategies.get(type);
    }
}
