package robo77.domain;

public enum CardType {

    SUM("number"),
    REVERSE("reverse"),
    DOUBLE("x2");

    private final String value;

    CardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
