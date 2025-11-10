package robo77.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import robo77.domain.Hand;
import robo77.domain.card.Card;
import robo77.domain.card.CardType;
import robo77.exception.ExceptionMessage;

class PlayerTest {

    @Test
    void 카드를_제출할_수_있다() {
        // given
        Card expectedCard = new Card(CardType.SUM, 2);
        Player player = new Player("jihyun", createHand());
        // when
        Card submittedCard = player.submitCard(expectedCard);
        // then
        assertAll(
                () -> assertThat(submittedCard).isEqualTo(expectedCard),
                () -> assertThat(player.getHand().hasCard(submittedCard)).isFalse()
        );
    }

    @Test
    void 손패에_없는_카드를_제출하면_예외를_반환한다() {
        // given
        Player player = new Player("jihyun", createHand());
        Card other = new Card(CardType.SUM, 11);
        // when & then
        assertThatThrownBy(() -> player.submitCard(other))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_CARD.getMessage());
    }

    @Test
    void 봇은_카드를_자동으로_제출할_수_있다() {
        // given
        Hand hand = createHand();
        Player bot = Player.byBot(hand);
        Card expectedCard = hand.getFirstCard();
        // when
        Card submittedCard = bot.submitCardByBot();
        // then
        assertAll(
                () -> assertThat(submittedCard).isEqualTo(expectedCard),
                () -> assertThat(bot.getHand().hasCard(expectedCard)).isFalse()
        );
    }

    @Test
    void 새_카드를_뽑을_수_있다() {
        // given
        Player player = new Player("jihyun", createHand());
        Card newCard = new Card(CardType.DOUBLE, 0);
        // when
        player.pickCard(newCard);
        // then
        assertThat(player.getHand().hasCard(newCard)).isTrue();
    }

    @Test
    void 봇인지_확인할_수_있다() {
        // given
        Player player = new Player("jihyun", createHand());
        Player bot = Player.byBot(createHand());
        // when & then
        assertAll(
                () -> assertThat(player.isBot()).isFalse(),
                () -> assertThat(bot.isBot()).isTrue()
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
