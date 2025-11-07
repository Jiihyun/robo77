package robo77.exception;


public enum ExceptionMessage {

    BLANK_INPUT("값을 입력하지 않으셨습니다."),
    EMPTY_PLAYER_NAME("사용자 이름은 빈문자열일 수 없습니다."),
    PLAYER_NAME_OUT_OF_RANGE("입력 가능한 이름 길이 범위를 초과하였습니다."),
    WRONG_PLAYER_NAME_FORMAT("잘못된 사용자 이름 형식입니다."),
    HOLDING_CARD_OUT_OF_RANGE("손패 범위를 초과하였습니다."),
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
