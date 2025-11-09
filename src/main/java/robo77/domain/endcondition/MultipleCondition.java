package robo77.domain.endcondition;

import robo77.domain.EndCondition;

public class MultipleCondition implements EndCondition {

    private static final int MULTIPLE = 11;

    @Override
    public boolean isSatisfiedBy(int sum) {
        return sum % MULTIPLE == 0
                && sum != 0;
    }
}
