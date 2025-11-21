package robo77.domain.player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Players {

    private final Deque<Player> players;

    public Players(List<Player> players) {
        this.players = new ArrayDeque<>(players);
    }

    public void reverseOrder() {
        List<Player> reversedPlayers = new ArrayList<>(players);
        Collections.reverse(reversedPlayers);
        players.clear();
        players.addAll(reversedPlayers);
    }

    public Player pollFirst() {
        return players.pollFirst();
    }

    public void pushToFront(Player player) {
        players.offerFirst(player);
    }

    public void pushToEnd(Player player) {
        players.offerLast(player);
    }

    public Player peekFirst() {
        return players.peekFirst();
    }

    public Player peekLast() {
        return players.peekLast();
    }

    public Player pollLast() {
        return players.pollLast();
    }

    public long getUniquePlayerCount() {
        return players.stream()
                .distinct()
                .count();
    }
}
