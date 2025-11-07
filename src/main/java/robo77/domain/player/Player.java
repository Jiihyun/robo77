package robo77.domain.player;

import robo77.domain.Hand;

public class Player {

    private final Name name;

    private final Hand hand;

    public Player(String name, Hand hand) {
        this.name = new Name(name);
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }
}
