package robo77.domain;

import robo77.domain.card.Card;
import robo77.domain.player.Player;

public record TurnResult(
        Player currentPlayer,
        Card submittedCard,
        Card newCard,
        boolean isGameOver
) {
}
