package robo77.domain.card;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CardGeneratorTest {

    @Test
    void 게임에_필요한_카드들을_생성한다() {
        // when
        List<Card> cards = CardGenerator.createCards();
        // then
        assertThat(cards).hasSize(56);
    }

    @ParameterizedTest
    @MethodSource("cardCountProvider")
    void 카드_값별_개수를_확인한다(CardType type, int cardValue, int expectedCount) {
        // given
        List<Card> cards = CardGenerator.createCards();
        // when
        long resultCount = countCardValue(cards, type, cardValue);
        // then
        assertThat(resultCount).isEqualTo(expectedCount);
    }

    private long countCardValue(List<Card> cards, CardType cardType, int cardValue) {
        return cards.stream()
                .filter(card -> card.getCardType() == cardType && card.getValue() == cardValue)
                .count();
    }

    static Stream<Arguments> cardCountProvider() {
        return Stream.of(
                Arguments.of(CardType.SUM, 0, 4),
                Arguments.of(CardType.SUM, 2, 3),
                Arguments.of(CardType.SUM, 3, 3),
                Arguments.of(CardType.SUM, 4, 3),
                Arguments.of(CardType.SUM, 5, 3),
                Arguments.of(CardType.SUM, 6, 3),
                Arguments.of(CardType.SUM, 7, 3),
                Arguments.of(CardType.SUM, 8, 3),
                Arguments.of(CardType.SUM, 9, 3),
                Arguments.of(CardType.SUM, 10, 8),
                Arguments.of(CardType.SUM, -10, 4),
                Arguments.of(CardType.SUM, 11, 1),
                Arguments.of(CardType.SUM, 22, 1),
                Arguments.of(CardType.SUM, 33, 1),
                Arguments.of(CardType.SUM, 44, 1),
                Arguments.of(CardType.SUM, 55, 1),
                Arguments.of(CardType.SUM, 66, 1),
                Arguments.of(CardType.SUM, 76, 1),
                Arguments.of(CardType.DOUBLE, 0, 4),
                Arguments.of(CardType.REVERSE, 0, 5)
        );
    }
}
