package robo77.view.output;

import java.util.List;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import robo77.domain.RoboGame;
import robo77.domain.TurnResult;
import robo77.domain.player.Player;
import robo77.view.CardRenderer;

public class DiscordGameOutput {

    private static final String ERROR_MESSAGE_PREFIX = "⚠️ ";

    public void showGameStart(SlashCommandInteractionEvent event, RoboGame game) {
        List<String> hand = formatHand(game.getCurrentPlayer());
        String message = """
                🎮 **로보77 게임을 시작합니다!**
                당신의 손패: %s
                
                `/play` 명령어로 카드를 내주세요.
                """.formatted(hand);
        reply(event, message, true);
    }

    private List<String> formatHand(Player player) {
        return player.getHand().getHoldingCards()
                .stream()
                .map(CardRenderer::cardToDisplayString)
                .toList();
    }

    public void showHand(SlashCommandInteractionEvent event, Player player) {
        reply(event, "🤚🏻당신의 손패: " + formatHand(player), true);
    }

    public void showGameQuit(SlashCommandInteractionEvent event) {
        reply(event, "✅ 현재 채널의 게임을 종료했습니다. `/startgame`으로 다시 시작할 수 있습니다.", false);
    }

    public void showError(SlashCommandInteractionEvent event, String message) {
        reply(event, ERROR_MESSAGE_PREFIX + message, true);
    }

    private void reply(SlashCommandInteractionEvent event, String message, boolean ephemeral) {
        if (event.isAcknowledged()) {
            event.getHook().sendMessage(message).setEphemeral(ephemeral).queue();
            return;
        }
        event.reply(message).setEphemeral(ephemeral).queue();
    }

    public void showNewCard(InteractionHook hook, TurnResult result) {
        String newCard = CardRenderer.cardToDisplayString(result.newCard());
        String message = "🃏 새로 받은 카드: `%s`".formatted(newCard);
        hook.sendMessage(message).setEphemeral(true).queue();
    }

    public void showSubmittedCard(InteractionHook hook, TurnResult result) {
        String playerName = result.currentPlayer().getName();
        String submittedCard = CardRenderer.cardToDisplayString(result.submittedCard());
        String message = "`%s`(이)가 `%s` 카드를 냈습니다.".formatted(playerName, submittedCard);
        hook.sendMessage(message).queue();
    }

    public void showWinner(InteractionHook hook, int sum, String winner) {
        String message = "\n**게임 종료!**\n합계가 `%d`이므로 `%s`의 승리입니다.".formatted(sum, winner);
        hook.sendMessage(message).queue();
    }
}
