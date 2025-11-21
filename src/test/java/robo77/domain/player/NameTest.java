package robo77.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import robo77.exception.ExceptionMessage;

class NameTest {

    @ParameterizedTest
    @ValueSource(strings = {"jihyun", "aa1", "qwerty10"})
    void 플레이어_이름을_등록한다(String name) {
        // when
        Name result = new Name(name);
        // then
        assertThat(result.getValue()).isEqualTo(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    void 플레이어_이름이_빈_문자열이면_예외가_발생한다(String name) {
        // when & then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.EMPTY_PLAYER_NAME.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"asdfghjklpoiuytrewqzxcvbnmlkjuiop", "a"})
    void 플레이어_이름의_길이가_범위_초과시_예외가_발생한다(String name) {
        // when & then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.PLAYER_NAME_OUT_OF_RANGE.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"지현", "a c", "ab+cd", "a..b"})
    void 잘못된_형식의_플레이어_이름이면_예외가_발생한다(String name) {
        // when & then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.WRONG_PLAYER_NAME_FORMAT.getMessage());
    }
}
