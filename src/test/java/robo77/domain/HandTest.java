package robo77.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import robo77.domain.card.Card;
import robo77.domain.card.CardType;
import robo77.exception.ExceptionMessage;

class HandTest {

    @Test
    void 유효한_손패_사이즈면_정상적으로_생성된다() {
        assertThatCode(this::createHand)
                .doesNotThrowAnyException();
    }

    @Test
    void 유효하지_않은_손패_사이즈면_예외가_발생한다() {
        assertThatThrownBy(() -> new Hand(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.HOLDING_CARD_OUT_OF_RANGE.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2, TRUE",
            "55, FALSE"
    })
    void 카드_보유_여부를_확인한다(int cardValue, boolean expectedResult) {
        // given
        Hand hand = createHand();
        Card card = new Card(CardType.SUM, cardValue);
        // when
        boolean result = hand.hasCard(card);
        // then
        assertThat(result).isEqualTo(expectedResult);
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
