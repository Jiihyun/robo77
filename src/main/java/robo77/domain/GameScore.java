package robo77.domain;

import robo77.domain.endcondition.MultipleCondition;
import robo77.domain.endcondition.OrCondition;
import robo77.domain.endcondition.OverLimitCondition;

public class GameScore {

    private final EndCondition endCondition;
    private int sum = 0;

    public GameScore(EndCondition endCondition) {
        this.endCondition = endCondition;
    }

    public static GameScore createWithEndCondition() {
        EndCondition endCondition = new OrCondition(
                new OverLimitCondition(),
                new MultipleCondition()
        );
        return new GameScore(endCondition);
    }

    public void add(int value) {
        sum += value;
    }

    public boolean isOverLimit() {
        return endCondition.isSatisfiedBy(sum);
    }

    public int getValue() {
        return sum;
    }
}
