package robo77.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import robo77.domain.Hand;
import robo77.domain.card.Card;
import robo77.domain.card.CardType;

class PlayersTest {

    @Test
    void 플레이어_순서를_뒤집는다() {
        // given
        Player player1 = Player.byBot(createHand());
        Player player2 = new Player("player2", createHand());
        Players players = new Players(List.of(player1, player2));
        // when
        players.reverseOrder();
        // then
        assertAll(
                () -> assertThat(players.peekFirst()).isEqualTo(player2),
                () -> assertThat(List.of(players.pollFirst(), players.pollFirst()))
                        .containsExactly(player2, player1)
        );
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
