package robo77.view;

import robo77.domain.card.Card;
import robo77.domain.card.CardType;

public final class CardRenderer {

    private CardRenderer() {
    }

    public static String cardToDisplayString(Card card) {
        if (card.getCardType() == CardType.SUM) {
            return String.valueOf(card.getValue());
        }
        return card.getCardType().getValue();
    }
}
