package robo77.domain.endcondition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrConditionTest {

    @ParameterizedTest
    @ValueSource(ints = {11, 22, 33, 44, 55, 66, 77, 78, 100})
    void 게임_종료_조건을_만족하는지_확인한다(int sum) {
        // given
        OrCondition orCondition = new OrCondition(
                new OverLimitCondition(),
                new MultipleCondition()
        );
        // when
        boolean isSatisfied = orCondition.isSatisfiedBy(sum);
        // then
        assertThat(isSatisfied).isTrue();
    }
}
