package robo77.discord;

import java.util.List;
import java.util.function.Consumer;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;
import robo77.domain.RoboGame;
import robo77.domain.TurnResult;
import robo77.domain.card.Card;
import robo77.domain.card.CardType;
import robo77.domain.card.submitstrategy.HumanSubmitStrategy;
import robo77.domain.player.Player;

public class GameCommandListener extends ListenerAdapter {

    private final GameSessionManager gameSessionManager;

    public GameCommandListener(GameSessionManager gameSessionManager) {
        this.gameSessionManager = gameSessionManager;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        try {
            Command.from(event.getName())
                    .execute(this, event);
        } catch (IllegalArgumentException e) {
            event.reply("⚠️ " + e.getMessage()).setEphemeral(true).queue();
        }
    }

    public void handleStartGame(SlashCommandInteractionEvent event) {
        String channelId = event.getChannel().getId();
        if (gameSessionManager.findExistingGame(channelId) != null) {
            event.reply("⚠️ 이미 진행 중인 게임이 있습니다. 게임을 새로 시작하려면 `/quit`을 먼저 실행해주세요.").setEphemeral(true).queue();
            return;
        }
        String playerName = event.getUser().getName();
        RoboGame roboGame = gameSessionManager.startGame(channelId, playerName);
        String message = formatStartMessage(roboGame);

        event.reply(message).setEphemeral(true).queue();
    }

    public void handleHand(SlashCommandInteractionEvent event) {
        String channelId = event.getChannel().getId();
        findAndExecuteGameAction(event, channelId, game -> {
            List<String> hand = formatHand(game.getCurrentPlayer());
            event.reply("당신의 손패: " + hand).setEphemeral(true).queue();
        });
    }

    public void handleQuit(SlashCommandInteractionEvent event) {
        String channelId = event.getChannel().getId();
        findAndExecuteGameAction(event, channelId, game -> {
            gameSessionManager.endGame(channelId);
            event.reply("✅ 현재 채널의 게임을 종료했습니다. `/startgame`으로 다시 시작할 수 있습니다.").queue();
        });
    }

    public void handlePlay(SlashCommandInteractionEvent event) {
        String channelId = event.getChannel().getId();
        findAndExecuteGameAction(event, channelId, game -> {
            String cardValue = event.getOption("card").getAsString();
            event.deferReply(true).queue(hook -> {
                try {
                    playTurns(hook, game, channelId, cardValue);
                } catch (Exception e) {
                    hook.sendMessage("⚠️ " + e.getMessage()).queue();
                }
            });
        });
    }

    private void playTurns(InteractionHook hook, RoboGame game, String channelId, String cardValue) {
        TurnResult playerResult = game.playTurn(new HumanSubmitStrategy(cardValue));
        StringBuilder publicMessage = new StringBuilder(formatPublicTurnMessage(playerResult));
        hook.sendMessage(formatNewCardMessage(playerResult)).queue();
        if (handleGameOverIfNeeded(game, channelId, playerResult, publicMessage)) {
            hook.sendMessage(publicMessage.toString()).queue();
            return;
        }
        processBotTurns(hook, game, channelId, publicMessage);
    }

    private void processBotTurns(InteractionHook hook, RoboGame game, String channelId, StringBuilder publicMessage) {
        List<TurnResult> botResults = game.playBotTurns();
        for (TurnResult botResult : botResults) {
            publicMessage.append("\n--------------------\n");
            publicMessage.append(formatTurnMessage(botResult));

            if (handleGameOverIfNeeded(game, channelId, botResult, publicMessage)) {
                break;
            }
        }
        hook.sendMessage(publicMessage.toString()).queue();
    }

    private void findAndExecuteGameAction(SlashCommandInteractionEvent event, String channelId, Consumer<RoboGame> gameAction) {
        RoboGame game = gameSessionManager.findExistingGame(channelId);
        if (game == null) {
            event.reply("⚠️ 진행 중인 게임이 없습니다. `/startgame`으로 먼저 게임을 시작해주세요.")
                    .setEphemeral(true).queue();
            return;
        }
        gameAction.accept(game);
    }

    private boolean handleGameOverIfNeeded(RoboGame game, String channelId, TurnResult result, StringBuilder response) {
        if (!result.isGameOver()) {
            return false;
        }
        Player winner = game.getWinner();
        response.append(formatGameOverMessage(game.getCurrentScore(), winner.getName()));
        gameSessionManager.endGame(channelId);
        return true;
    }

    private String formatStartMessage(RoboGame roboGame) {
        List<String> hand = formatHand(roboGame.getCurrentPlayer());
        return """
                🎮 **로보77 게임을 시작합니다!**
                당신의 손패: %s
                
                `/play` 명령어로 카드를 내주세요.
                """.formatted(hand);
    }

    private List<String> formatHand(Player player) {
        return player.getHand().getHoldingCards()
                .stream()
                .map(this::cardToDisplayString)
                .toList();
    }

    private String formatPublicTurnMessage(TurnResult result) {
        String playerName = result.currentPlayer().getName();
        String submitted = cardToDisplayString(result.submittedCard());
        return String.format("`%s`(이)가 `%s` 카드를 냈습니다.", playerName, submitted);
    }

    private String formatNewCardMessage(TurnResult result) {
        String newCard = cardToDisplayString(result.newCard());
        return String.format("🎴 새로 받은 카드: `%s`", newCard);
    }

    private String formatTurnMessage(TurnResult result) {
        String playerName = result.currentPlayer().getName();
        String submitted = cardToDisplayString(result.submittedCard());
        return String.format("`%s`(이)가 `%s` 카드를 냈습니다.", playerName, submitted);
    }

    private String cardToDisplayString(Card card) {
        if (card.getCardType() == CardType.SUM) {
            return String.valueOf(card.getValue());
        }
        return card.getCardType().getValue();
    }

    private String formatGameOverMessage(int sum, String winnerName) {
        return String.format("\n\n**게임 종료!** \n 합계가 `%d`이므로 `%s`의 승리입니다.", sum, winnerName);
    }
}
