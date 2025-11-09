package robo77.domain.endcondition;

import robo77.domain.EndCondition;

public class OverLimitCondition implements EndCondition {

    private static final int LIMIT = 77;

    @Override
    public boolean isSatisfiedBy(int sum) {
        return sum > LIMIT;
    }
}
