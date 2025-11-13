package robo77.discord;

import java.util.List;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import robo77.domain.RoboGame;
import robo77.domain.card.Card;
import robo77.domain.card.CardType;
import robo77.service.GameSessionManager;
import robo77.service.TurnResult;

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
        if (event.getName().equals(Command.QUIT.getCommand())) {
            handleQuit(event, channelId);
        }
        if (event.getName().equals(Command.HAND.getCommand())) {
            handleHand(event, channelId);
        }
        if (event.getName().equals(Command.PLAY.getCommand())) {
            handlePlay(event, channelId);
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

    private void handleHand(SlashCommandInteractionEvent event, String channelId) {
        RoboGame roboGame = gameSessionManager.findExistingGame(channelId);
        if (roboGame == null) {
            event.reply("⚠️ 진행 중인 게임이 없습니다. `/startgame`으로 먼저 게임을 시작해주세요.").setEphemeral(true).queue();
            return;
        }
        List<String> hand = roboGame.getCurrentPlayer().getHand().getHoldingCards()
                .stream()
                .map(this::cardToDisplayString)
                .toList();
        event.reply("당신의 손패: " + hand).setEphemeral(true).queue();
    }

    private void handleQuit(SlashCommandInteractionEvent event, String channelId) {
        if (gameSessionNotExists(channelId)) {
            event.reply("⚠️ 종료할 게임이 없습니다.").setEphemeral(true).queue();
            return;
        }
        gameSessionManager.endGame(channelId);
        event.reply("✅ 현재 채널의 게임을 종료했습니다. `/startgame`으로 다시 시작할 수 있습니다.").queue();
    }

    private boolean gameSessionNotExists(String channelId) {
        return gameSessionManager.findExistingGame(channelId) == null;
    }

    private void handlePlay(SlashCommandInteractionEvent event, String channelId) {
        RoboGame game = gameSessionManager.findExistingGame(channelId);
        if (game == null) {
            event.reply("⚠️ 진행 중인 게임이 없습니다. `/startgame`으로 먼저 게임을 시작해주세요.").setEphemeral(true).queue();
            return;
        }

        String cardValue = event.getOption("card").getAsString();
        StringBuilder responseBuilder = new StringBuilder();

        try {
            TurnResult playerResult = processTurn(game, channelId, cardValue, responseBuilder);
            if (playerResult.isGameOver()) {
                event.reply(responseBuilder.toString()).queue();
                return;
            }
            processBotTurns(game, channelId, responseBuilder);
            event.reply(responseBuilder.toString()).queue();
        } catch (IllegalArgumentException | IllegalStateException e) {
            event.reply("⚠️ " + e.getMessage()).setEphemeral(true).queue();
        }
    }

    private void processBotTurns(RoboGame game, String channelId, StringBuilder sb) {
        while (game.isPlaying() && game.getCurrentPlayer().isBot()) {
            sb.append("\n--------------------\n");

            TurnResult botResult = gameSessionManager.playTurn(channelId, null);
            sb.append(formatTurnMessage(botResult));

            if (botResult.isGameOver()) {
                sb.append(formatGameOverMessage(game.getCurrentScore(), botResult.winner().getName()));
                gameSessionManager.endGame(channelId);
                break;
            }
        }
    }

    private TurnResult processTurn(RoboGame game, String channelId, String cardValue, StringBuilder sb) {
        TurnResult result = gameSessionManager.playTurn(channelId, cardValue);

        sb.append(formatTurnMessage(result));

        if (result.isGameOver()) {
            sb.append(formatGameOverMessage(game.getCurrentScore(), result.winner().getName()));
            gameSessionManager.endGame(channelId);
        }
        return result;
    }

    private String formatTurnMessage(TurnResult result) {
        String playerName = result.currentPlayer().getName();
        String submitted = cardToDisplayString(result.submittedCard());

        if (playerName.equals("bot")) {
            return String.format("`%s`(이)가 `%s` 카드를 냈습니다.",
                    playerName, submitted);
        }
        String newCard = cardToDisplayString(result.newCard());
        return String.format("`%s`(이)가 `%s` 카드를 냈습니다. 새로 받은 카드는 `%s` 입니다.",
                playerName, submitted, newCard);
    }

    private String formatGameOverMessage(int sum, String winnerName) {
        return String.format("\n\n**게임 종료!** \n 합계가 `%d`이므로 `%s`의 승리입니다.", sum, winnerName);
    }
}
