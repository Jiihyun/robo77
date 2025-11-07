package robo77.domain;

import java.util.Arrays;
import robo77.exception.ExceptionMessage;

public enum CardType {

    SUM("number"),
    REVERSE("reverse"),
    DOUBLE("x2");

    private final String value;

    CardType(String value) {
        this.value = value;
    }

    public static CardType from(String cardToSubmit) {
        return Arrays.stream(CardType.values())
                .filter(type -> type.value.equals(cardToSubmit))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.CARD_TYPE_NOT_FOUND.getMessage()));
    }

    public String getValue() {
        return value;
    }
}
