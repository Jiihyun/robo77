package robo77;

import java.util.List;
import robo77.domain.Card;
import robo77.domain.CardType;
import robo77.domain.Deck;
import robo77.domain.Hand;
import robo77.domain.player.Player;
import robo77.domain.turn.TurnManager;
import robo77.domain.turn.TurnPolicy;
import robo77.domain.turn.TurnPolicyFactory;
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

        play(player, bot, deck);
    }

    private void play(Player player, Player bot, Deck deck) {
        TurnManager turnManager = new TurnManager(List.of(player, bot));
        int sum = 0;
        while (true) {
            Player currentPlayer = turnManager.getCurrentPlayer();

            Card submittedCard = getSubmittedCard(currentPlayer, sum, deck);
            Card newCard = deck.shareCard();

            sum = processCardEffect(currentPlayer, submittedCard, newCard, sum);
            if (hasEndCondition(sum)) {
                Player winner = getWinner(turnManager, currentPlayer);
                outputView.showWinner(sum, winner.getName());
                break;
            }
            TurnPolicy turnPolicy = TurnPolicyFactory.get(submittedCard.getCardType());
            turnPolicy.nextTurnPlayer(turnManager);
        }
    }

    private Card getSubmittedCard(Player currentPlayer, int sum, Deck deck) {
        if (currentPlayer.getName().equals("bot")) {
            Card botNewCard = deck.shareCard();
            return currentPlayer.submitCardByBot(botNewCard);
        }
        outputView.showSumAndHandMessage(sum, currentPlayer.getHand());
        String cardToSubmit = inputView.readCardToSubmit();
        Card submittedCard = Card.from(cardToSubmit);

        if (!currentPlayer.hasSubmittedCard(submittedCard)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_CARD.getMessage());
        }
        return submittedCard;
    }

    private int processCardEffect(Player player, Card submittedCard, Card newCard, int sum) {
        player.submitCard(submittedCard, newCard);
        outputView.showSubmittedCard(player.getName(), submittedCard);

        if (submittedCard.getCardType() == CardType.SUM) {
            return sum + submittedCard.getValue();
        }
        return sum;
    }

    private Player getWinner(TurnManager turnManager, Player currentPlayer) {
        return turnManager.getPlayers().stream()
                .filter(player -> !player.equals(currentPlayer))
                .findFirst()
                .orElse(currentPlayer);
    }

    private boolean hasEndCondition(int sum) {
        return sum > 77 || (sum % 11 == 0 && sum != 0);
    }
}
