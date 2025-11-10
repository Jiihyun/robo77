package robo77.domain.card;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CardTest {

    @ParameterizedTest
    @CsvSource(value = {
            "76, SUM",
            "-10, SUM",
            "reverse, REVERSE",
            "x2, DOUBLE",
    })
    void 카드를_생성할_수_있다(String cardToSubmit, CardType cardType) {
        // when
        Card card = Card.from(cardToSubmit);
        // then
        assertThat(card.getCardType()).isEqualTo(cardType);
    }
}
