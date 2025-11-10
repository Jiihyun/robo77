package robo77.domain.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static robo77.domain.card.Deck.DEFAULT_HAND_SIZE;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import robo77.domain.Hand;
import robo77.exception.ExceptionMessage;

class DeckTest {

    @Test
    void 덱을_초기화한다() {
        // given
        int expectedSize = 1;
        List<Card> cards = List.of(new Card(CardType.SUM, 1));
        // when
        Deck deck = new Deck(cards);
        // then
        assertThat(deck.getCards()).hasSize(expectedSize);
    }

    @Test
    void 핸드_크기만큼_카드를_나눠준다() {
        // given
        Deck deck = createDeck();
        int deckSizeBeforeDraw = deck.getCards().size();
        int expectedDeckSizeAfterDraw = deckSizeBeforeDraw - DEFAULT_HAND_SIZE;
        // when
        Hand hand = deck.drawCards();
        // then
        assertAll(
                () -> assertThat(hand.getHoldingCards()).hasSize(DEFAULT_HAND_SIZE),
                () -> assertThat(deck.getCards()).hasSize(expectedDeckSizeAfterDraw)
        );
    }

    @Test
    void 카드_한_장을_나눠준다() {
        // given
        Deck deck = createDeck();
        Card expectedCard = deck.getCards().getFirst();
        // when
        Card card = deck.drawCard();
        // then
        assertThat(card).isEqualTo(expectedCard);
    }

    @Test
    void 나눠줄_카드가_없으면_예외를_반환한다() {
        // given
        Deck deck = new Deck(List.of());
        // when & then
        assertThatThrownBy(deck::drawCard)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ExceptionMessage.CARD_NOT_EXISTS.getMessage());
    }

    private Deck createDeck() {
        return new Deck(new ArrayList<>(List.of(
                new Card(CardType.SUM, 0),
                new Card(CardType.SUM, 2),
                new Card(CardType.SUM, 3),
                new Card(CardType.SUM, 4),
                new Card(CardType.SUM, 5)
        )));
    }
}
