package robo77.domain.turn.turnpolicy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import robo77.domain.Hand;
import robo77.domain.card.Card;
import robo77.domain.card.CardType;
import robo77.domain.player.Player;
import robo77.domain.player.Players;

class ReverseTurnPolicyTest {

    @Test
    void 인원이_두명밖에_없을_때_다음_순서의_플레이어를_구한다() {
        // given
        Player player1 = Player.byBot(createHand());
        Player player2 = new Player("player2", createHand());
        Players players = new Players(List.of(player1, player2));

        ReverseTurnPolicy reverseTurnPolicy = new ReverseTurnPolicy();
        // when
        Player nextTurnPlayer = reverseTurnPolicy.findNextTurnPlayer(players);
        // then
        assertThat(nextTurnPlayer).isEqualTo(player1);
    }

    @Test
    void 다음_순서의_플레이어를_구한다() {
        // given
        Player player1 = Player.byBot(createHand());
        Player player2 = new Player("player2", createHand());
        Player player3 = new Player("player3", createHand());
        Players players = new Players(List.of(player1, player2, player3));

        ReverseTurnPolicy reverseTurnPolicy = new ReverseTurnPolicy();
        // when
        Player nextTurnPlayer = reverseTurnPolicy.findNextTurnPlayer(players);
        // then
        assertThat(nextTurnPlayer).isEqualTo(player3);
    }

    private Hand createHand() {
        return new Hand(new ArrayList<>(List.of(
                new Card(CardType.SUM, 2),
                new Card(CardType.SUM, 3),
                new Card(CardType.SUM, 4),
                new Card(CardType.SUM, 5),
                new Card(CardType.SUM, 6)
        )));
    }
}
