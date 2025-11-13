package robo77.service;

import robo77.domain.card.Card;
import robo77.domain.player.Player;

public record TurnResult(
        Player currentPlayer,
        Card submittedCard,
        Card newCard,
        boolean isGameOver,
        Player winner
) {
}
