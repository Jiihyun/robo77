package robo77.discord;

import java.util.List;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import robo77.domain.RoboGame;
import robo77.domain.card.Card;
import robo77.domain.card.CardType;
import robo77.service.GameSessionManager;

public class GameCommandListener extends ListenerAdapter {

    private final GameSessionManager gameSessionManager;

    public GameCommandListener(GameSessionManager gameSessionManager) {
        this.gameSessionManager = gameSessionManager;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String channelId = event.getChannel().getId();

        if (event.getName().equals(Command.START_GAME.getCommand())) {
            handleStartGame(event, channelId);
        }
    }

    private void handleStartGame(SlashCommandInteractionEvent event, String channelId) {
        if (gameSessionManager.findExistingGame(channelId) != null) {
            event.reply("⚠️ 이미 진행 중인 게임이 있습니다. 게임을 새로 시작하려면 `/quit`을 먼저 실행해주세요.").setEphemeral(true).queue();
            return;
        }
        String playerName = event.getUser().getName();
        RoboGame roboGame = gameSessionManager.startGame(channelId, playerName);
        String message = getReplyMessage(roboGame);
        event.reply(message)
                .setEphemeral(true)
                .queue();
    }

    @NotNull
    private String getReplyMessage(RoboGame roboGame) {
        List<String> hand = roboGame.getCurrentPlayer().getHand().getHoldingCards()
                .stream()
                .map(this::cardToDisplayString)
                .toList();

        return """
                🎮 **로보77 게임을 시작합니다!**
                당신의 손패: %s
                
                `/play` 명령어로 카드를 내주세요.
                """.formatted(hand);
    }

    private String cardToDisplayString(Card card) {
        if (card.getCardType() == CardType.SUM) {
            return String.valueOf(card.getValue());
        }
        return card.getCardType().getValue();
    }
}
