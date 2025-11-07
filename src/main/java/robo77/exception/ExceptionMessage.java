package robo77.exception;


public enum ExceptionMessage {

    BLANK_INPUT("값을 입력하지 않으셨습니다."),
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
