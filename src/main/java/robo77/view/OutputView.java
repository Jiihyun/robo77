package robo77.view;

import java.util.List;
import robo77.domain.Card;
import robo77.domain.CardType;
import robo77.domain.Hand;
import robo77.io.Writer;

public class OutputView {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";

    private final Writer writer;

    public OutputView(Writer writer) {
        this.writer = writer;
    }

    public void showSumAndHandMessage(int sum, Hand hand) {
//        writer.writeLine(NEW_LINE + "현재 합계: " + sum);
        List<String> cards = hand.getHoldingCards().stream()
                .map(this::cardToDisplayString)
                .toList();
        writer.writeLine(NEW_LINE + "당신의 카드: " + cards);
    }

    public void showSubmittedCard(String name, Card card) {
        writer.writeLine(NEW_LINE + name + "님께서 " + cardToDisplayString(card) + "를 제출하셨습니다.");
    }

    private String cardToDisplayString(Card card) {
        if (card.getCardType() == CardType.SUM) {
            return String.valueOf(card.getValue());
        }
        return card.getCardType().getValue();
    }

    public void showWinner(int sum, String winner) {
        writer.writeLine(NEW_LINE + "게임 종료");
        writer.writeLine("합계가 " + sum + "이므로 " + winner + "의 승리입니다.");
    }

    public void showError(String message) {
        writer.writeLine(ERROR_MESSAGE_PREFIX + message);
    }
}
