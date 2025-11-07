package robo77.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void 덱을_초기화한다() {
        // given
        int expectedSize = 56;
        // when
        Deck deck = new Deck();
        // then
        assertAll(
                () -> assertThat(deck.getCards()).hasSize(expectedSize),
                () -> assertThat(getCount(deck, CardType.REVERSE)).isEqualTo(5),
                () -> assertThat(getCount(deck, CardType.DOUBLE)).isEqualTo(4),
                () -> assertThat(getCount(deck, CardType.SUM)).isEqualTo(47)
        );
    }

    private long getCount(Deck deck, CardType cardType) {
        return deck.getCards().stream()
                .filter(card -> card.getCardType() == cardType)
                .count();
    }
}
