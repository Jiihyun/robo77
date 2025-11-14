package robo77.exception;


public enum ExceptionMessage {

    BLANK_INPUT("값을 입력하지 않으셨습니다."),
    EMPTY_PLAYER_NAME("사용자 이름은 빈문자열일 수 없습니다."),
    PLAYER_NAME_OUT_OF_RANGE("입력 가능한 이름 길이 범위를 초과하였습니다."),
    WRONG_PLAYER_NAME_FORMAT("잘못된 사용자 이름 형식입니다."),
    HOLDING_CARD_OUT_OF_RANGE("손패 범위를 초과하였습니다."),
    CARD_TYPE_NOT_FOUND("존재하지 않는 카드 타입입니다."),
    INVALID_CARD("손패에 있는 카드만 제출할 수 있습니다."),
    CARD_NOT_EXISTS("카드가 더이상 존재하지 않습니다."),

    // DISCORD
    COMMAND_NOT_FOUND("존재하지 않는 명령어입니다."),
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
