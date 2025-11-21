package robo77.domain.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import robo77.exception.ExceptionMessage;

class CardTypeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "number, SUM",
            "reverse, REVERSE",
            "x2, DOUBLE",
    })
    void 카드타입_값으로부터_알맞은_카드타입을_찾을_수_있다(String cardToSubmit, CardType expectedCardType) {
        // when
        CardType resultCardType = CardType.from(cardToSubmit);
        // then
        assertThat(resultCardType).isEqualTo(expectedCardType);
    }

    @Test
    void 카드타입_값으로부터_일치하는_타입이_없으면_예외를_반환한다() {
        // when & then
        assertThatThrownBy(() -> CardType.from("unknown"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.CARD_TYPE_NOT_FOUND.getMessage());
    }
}
