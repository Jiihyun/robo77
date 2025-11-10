package robo77.domain.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import robo77.domain.Hand;

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

    @Test
    void 플레이어에게_카드를_나눠준다() {
        // given
        Deck deck = new Deck();
        int sizeBeforeShare = deck.getCards().size();
        int sizeAfterShare = sizeBeforeShare - 5;
        // when
        Hand hand = deck.drawCards();
        // then
        assertThat(deck.getCards()).hasSize(sizeAfterShare);
    }
}
