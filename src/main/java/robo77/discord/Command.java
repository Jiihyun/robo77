package robo77.discord;

import java.util.Arrays;
import java.util.function.BiConsumer;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import robo77.exception.ExceptionMessage;

public enum Command {

    START_GAME("startgame", "로보77 게임을 새로 시작합니다.", DiscordCommandListener::handleStartGame),
    HAND("hand", "현재 손에 들고 있는 카드를 확인합니다.", DiscordCommandListener::handleHand),
    PLAY("play", "손에 들고 있는 카드 중에서 한 장을 제출합니다.", DiscordCommandListener::handlePlay),
    QUIT("quit", "게임을 종료합니다.", DiscordCommandListener::handleQuit),
    GUIDE("guide", "빠르게 게임 규칙을 파악합니다.", DiscordCommandListener::handleGuide);

    private final String command;
    private final String description;
    private final BiConsumer<DiscordCommandListener, SlashCommandInteractionEvent> handler;

    Command(String command, String description,
            BiConsumer<DiscordCommandListener, SlashCommandInteractionEvent> handler) {
        this.command = command;
        this.description = description;
        this.handler = handler;
    }

    public static Command from(String command) {
        return Arrays.stream(values())
                .filter(it -> it.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.COMMAND_NOT_FOUND.getMessage()));
    }

    public void execute(DiscordCommandListener listener, SlashCommandInteractionEvent event) {
        handler.accept(listener, event);
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
