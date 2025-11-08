package robo77.domain.turn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import robo77.domain.player.Player;

public class TurnManager {

    private final Deque<Player> players;

    public TurnManager(List<Player> players) {
        this.players = new ArrayDeque<>(players);
    }

    public void reverseOrder() {
        List<Player> playerList = new ArrayList<>(players);
        Collections.reverse(playerList);
        players.clear();
        players.addAll(playerList);
    }

    public Player getCurrentPlayer() {
        return players.peekFirst();
    }

    public Player pollCurrentPlayer() {
        return players.pollFirst();
    }

    public void pushToEnd(Player player) {
        players.offerLast(player);
    }

    public void pushToFront(Player player) {
        players.offerFirst(player);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public Deque<Player> getPlayers() {
        return players;
    }
}
