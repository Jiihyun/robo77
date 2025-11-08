package robo77;

import java.util.List;
import robo77.domain.Card;
import robo77.domain.CardType;
import robo77.domain.Deck;
import robo77.domain.Hand;
import robo77.domain.player.Player;
import robo77.exception.ExceptionMessage;
import robo77.view.InputView;
import robo77.view.OutputView;

public class RoboGame {

    private final InputView inputView;
    private final OutputView outputView;

    public RoboGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        String playerName = inputView.readPlayerName();
        Deck deck = new Deck();
        List<Hand> hands = deck.shareCards();
        Player player = new Player(playerName, hands.getFirst());
        Player bot = new Player("bot", hands.getLast());
        int sum = 0;
        play(sum, player, deck, bot);
    }

    private void play(int sum, Player player, Deck deck, Player bot) {
        while (true) {
            outputView.showSumAndHandMessage(sum, player.getHand());
            String cardToSubmit = inputView.readCardToSubmit();
            Card submittedCard = Card.from(cardToSubmit);
            boolean hasCard = player.hasSubmittedCard(submittedCard);
            if (!hasCard) {
                throw new IllegalArgumentException(ExceptionMessage.INVALID_CARD.getMessage());
            }
            sum = getSum(deck, player, submittedCard, sum);
            if (hasEndCondition(sum)) {
                outputView.showWinner(sum, bot.getName());
                break;
            }
            sum = getSum(deck, bot, sum);
            if (hasEndCondition(sum)) {
                outputView.showWinner(sum, player.getName());
                break;
            }
        }
    }

    private int getSum(Deck deck, Player player, Card submittedCard, int sum) {
        Card newCard = deck.shareCard();
        player.submitCard(submittedCard, newCard);
        if (submittedCard.getCardType() == CardType.SUM) {
            sum += submittedCard.getValue();
        }
        outputView.showSubmittedCard(player.getName(), submittedCard);
        return sum;
    }

    private int getSum(Deck deck, Player bot, int sum) {
        Card newCard2 = deck.shareCard();
        Card submittedCard2 = bot.submitCardByBot(newCard2);
        if (submittedCard2.getCardType() == CardType.SUM) {
            sum += submittedCard2.getValue();
        }
        outputView.showSubmittedCard(bot.getName(), submittedCard2);
        return sum;
    }

    private boolean hasEndCondition(int sum) {
        return sum > 77 || sum % 11 == 0;
    }
}
