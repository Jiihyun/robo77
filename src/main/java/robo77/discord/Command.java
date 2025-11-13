package robo77.discord;

public enum Command {

    START_GAME("startgame", "로보77 게임을 새로 시작합니다."),
    HAND("hand", "현재 손에 들고 있는 카드를 확인합니다."),
    PLAY("play", "손에 들고 있는 카드 중에서 한 장을 제출합니다."),
    QUIT("quit", "게임을 종료합니다."),
    ;

    private final String command;
    private final String description;

    Command(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
