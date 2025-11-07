package robo77.view;

import java.util.List;
import robo77.domain.Card;
import robo77.domain.CardType;
import robo77.domain.Hand;
import robo77.io.Writer;

public class OutputView {

    private final Writer writer;

    public OutputView(Writer writer) {
        this.writer = writer;
    }

    public void showSumAndHandMessage(int sum, Hand hand) {
        writer.writeLine("현재 합계: " + sum);
        List<String> cards = hand.getHoldingCards().stream()
                .map(this::cardToDisplayString)
                .toList();
        writer.writeLine("당신의 카드: " + cards);
    }

    private String cardToDisplayString(Card card) {
        if (card.getCardType() == CardType.SUM) {
            return String.valueOf(card.getValue());
        }
        return card.getCardType().getValue();
    }
}
