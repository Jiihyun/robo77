package robo77.domain.turn;

import java.util.List;
import robo77.domain.Card;
import robo77.domain.Deck;
import robo77.domain.player.Player;
import robo77.domain.player.Players;

public class TurnManager {

    private final Players players;

    public TurnManager(Players players) {
        this.players = players;
    }

    public static TurnManager createTurn(String playerName, Deck deck) {
        Player player = new Player(playerName, deck.shareCards());
        Player bot = Player.byBot(deck.shareCards());
        return new TurnManager(new Players(List.of(player, bot)));
    }

    public Player findNextTurnPlayer(Card submittedCard) {
        TurnPolicy turnPolicy = TurnPolicyFactory.get(submittedCard.getCardType());
        return turnPolicy.findNextTurnPlayer(players);
    }

    public Player getCurrentPlayer() {
        return players.getCurrentPlayer();
    }
}
