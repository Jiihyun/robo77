package robo77.view.output;

import java.util.List;
import robo77.domain.Hand;
import robo77.domain.card.Card;
import robo77.view.CardRenderer;

public class ConsoleOutput {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";

    public void showSumAndHandMessage(Hand hand) {
        List<String> cards = hand.getHoldingCards().stream()
                .map(CardRenderer::cardToDisplayString)
                .toList();
        System.out.println(NEW_LINE + "당신의 카드: " + cards);
    }

    public void showSubmittedCard(String name, Card card) {
        String submittedCard = CardRenderer.cardToDisplayString(card);
        System.out.println(NEW_LINE + name + "님께서 " + submittedCard + "를 제출하셨습니다.");
    }

    public void showWinner(int sum, String winner) {
        System.out.println(NEW_LINE + "게임 종료");
        System.out.println("합계가 " + sum + "이므로 " + winner + "의 승리입니다.");
    }

    public void showError(String message) {
        System.out.println(ERROR_MESSAGE_PREFIX + message);
    }
}
