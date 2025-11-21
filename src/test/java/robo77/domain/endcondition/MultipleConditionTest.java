package robo77.domain.endcondition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MultipleConditionTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 21, 34, 76})
    void 게임_종료_조건을_만족하지_않는지_확인한다(int sum) {
        // given
        MultipleCondition multipleCondition = new MultipleCondition();
        // when
        boolean isSatisfied = multipleCondition.isSatisfiedBy(sum);
        // then
        assertThat(isSatisfied).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 22, 33, 44, 55, 66, 77})
    void 게임_종료_조건을_만족하는지_확인한다(int sum) {
        // given
        MultipleCondition multipleCondition = new MultipleCondition();
        // when
        boolean isSatisfied = multipleCondition.isSatisfiedBy(sum);
        // then
        assertThat(isSatisfied).isTrue();
    }
}
