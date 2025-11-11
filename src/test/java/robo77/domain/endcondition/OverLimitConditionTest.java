package robo77.domain.endcondition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OverLimitConditionTest {

    @ParameterizedTest
    @CsvSource({
            "77, false",
            "78, true"
    })
    void 게임_종료_조건_만족_여부를_판단한다(int number, boolean expected) {
        // given
        OverLimitCondition overLimitCondition = new OverLimitCondition();
        // when
        boolean result = overLimitCondition.isSatisfiedBy(number);
        // then
        assertThat(result).isEqualTo(expected);
    }
}
