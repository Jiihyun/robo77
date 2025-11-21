package robo77.domain.player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import robo77.exception.ExceptionMessage;

public class Name {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 32;
    private static final Pattern FORMAT_PATTERN = Pattern.compile("^(?!.*\\.\\.)[0-9a-z._]+$");

    private final String value;

    public Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        validateLength(value);
        validateFormat(value);
    }

    private void validateLength(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessage.EMPTY_PLAYER_NAME.getMessage());
        }
        if (isOutOfRange(value.length())) {
            throw new IllegalArgumentException(ExceptionMessage.PLAYER_NAME_OUT_OF_RANGE.getMessage());
        }
    }

    private boolean isOutOfRange(int length) {
        return length < MIN_LENGTH || length > MAX_LENGTH;
    }

    private void validateFormat(String value) {
        Matcher matcher = FORMAT_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(ExceptionMessage.WRONG_PLAYER_NAME_FORMAT.getMessage());
        }
    }

    public boolean isSame(String bot) {
        return value.equals(bot);
    }

    public String getValue() {
        return value;
    }
}
