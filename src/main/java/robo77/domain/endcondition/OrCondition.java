package robo77.domain.endcondition;

import java.util.List;
import robo77.domain.EndCondition;

public class OrCondition implements EndCondition {

    private final List<EndCondition> conditions;

    public OrCondition(EndCondition... conditions) {
        this.conditions = List.of(conditions);
    }

    @Override
    public boolean isGameOver(int sum) {
        return conditions.stream()
                .anyMatch(endCondition -> endCondition.isGameOver(sum));
    }
}
