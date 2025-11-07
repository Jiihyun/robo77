package robo77;

import java.util.List;
import robo77.domain.Deck;
import robo77.domain.Hand;
import robo77.domain.player.Player;
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
        Player bot = new Player(playerName, hands.getLast());
        outputView.showSumAndHandMessage(0, player.getHand());
    }
}
