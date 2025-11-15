package robo77.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import robo77.domain.card.Card;
import robo77.domain.card.CardGenerator;
import robo77.domain.card.CardType;
import robo77.domain.card.Deck;
import robo77.domain.card.submitstrategy.HumanSubmitStrategy;
import robo77.domain.player.Player;
import robo77.domain.player.Players;
import robo77.domain.turn.TurnManager;

class RoboGameTest {

    @Test
    void 현재_플레이어를_조회한다() {
        // given
        Deck deck = new Deck(CardGenerator.createCards());
        Player expectedPlayer = new Player("player1", createHand());
        TurnManager turnManager = new TurnManager(new Players(List.of(expectedPlayer)));
        Referee referee = new Referee();

        RoboGame roboGame = new RoboGame(deck, turnManager, referee);
        // when
        Player currentPlayer = roboGame.getCurrentPlayer();
        // then
        assertThat(currentPlayer).isEqualTo(expectedPlayer);
    }

    @Test
    void 현재_점수를_조회한다() {
        // given
        Deck deck = new Deck(CardGenerator.createCards());
        Player player = new Player("player1", createHand());
        TurnManager turnManager = new TurnManager(new Players(List.of(player)));
        Referee referee = new Referee();

        RoboGame roboGame = new RoboGame(deck, turnManager, referee);
        referee.recordScore(5);
        referee.recordScore(3);
        // when
        int currentScore = roboGame.getCurrentScore();
        // then
        assertThat(currentScore).isEqualTo(8);
    }

    @Test
    void 게임이_진행중이면_true를_반환한다() {
        // given
        Deck deck = new Deck(CardGenerator.createCards());
        Player player = new Player("player1", createHand());
        TurnManager turnManager = new TurnManager(new Players(List.of(player)));
        Referee referee = new Referee();

        RoboGame roboGame = new RoboGame(deck, turnManager, referee);
        // when
        referee.recordScore(10);
        // then
        assertThat(roboGame.isPlaying()).isTrue();
    }

    @Test
    void 점수가_한계치에_도달하면_false를_반환한다() {
        // given
        Deck deck = new Deck(CardGenerator.createCards());
        Player player = new Player("player1", createHand());
        TurnManager turnManager = new TurnManager(new Players(List.of(player)));
        Referee referee = new Referee();

        RoboGame roboGame = new RoboGame(deck, turnManager, referee);
        referee.recordScore(77);
        // when
        boolean isPlaying = roboGame.isPlaying();
        // then
        assertThat(isPlaying).isFalse();
    }

    @Test
    void 봇의_턴에서_규칙에_맞는_카드를_제출한다() {
        // given
        Deck deck = new Deck(CardGenerator.createCards());
        Player player1 = Player.byBot(createHand());
        Player player2 = new Player("player1", createHand());
        TurnManager turnManager = new TurnManager(new Players(List.of(player1, player2)));
        Referee referee = new Referee();

        RoboGame roboGame = new RoboGame(deck, turnManager, referee);
        // when
        List<TurnResult> turnResults = roboGame.playBotTurns();
        // then
        assertThat(turnResults.getFirst().submittedCard().getValue()).isEqualTo(2);
    }

    @Test
    void 카드를_뽑으면_Deck에서_카드가_반환된다() {
        // given
        Deck deck = new Deck(CardGenerator.createCards());
        Player player1 = new Player("player1", createHand());
        Player player2 = Player.byBot(createHand());
        TurnManager turnManager = new TurnManager(new Players(List.of(player1, player2)));
        Referee referee = new Referee();

        RoboGame roboGame = new RoboGame(deck, turnManager, referee);
        // when
        TurnResult turnResult = roboGame.playTurn(new HumanSubmitStrategy("11"));
        // then
        assertThat(turnResult.submittedCard().getValue()).isEqualTo(11);
    }

    @Test
    void 게임_종료시_승자를_조회한다() {
        // given
        Deck deck = new Deck(CardGenerator.createCards());
        Player player1 = new Player("player1", createHand());
        Player player2 = Player.byBot(createHand());
        TurnManager turnManager = new TurnManager(new Players(List.of(player1, player2)));
        Referee referee = new Referee();

        RoboGame roboGame = new RoboGame(deck, turnManager, referee);
        roboGame.playTurn(new HumanSubmitStrategy("11"));
        // when
        Player winner = roboGame.getWinner();
        // then
        assertThat(winner).isEqualTo(player2);
    }

    private Hand createHand() {
        return new Hand(new ArrayList<>(List.of(
                new Card(CardType.SUM, 2),
                new Card(CardType.SUM, 3),
                new Card(CardType.SUM, 4),
                new Card(CardType.SUM, 5),
                new Card(CardType.SUM, 11)
        )));
    }
}
