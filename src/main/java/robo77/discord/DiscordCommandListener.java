package robo77.discord;

import java.util.List;
import java.util.function.Consumer;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import robo77.domain.RoboGame;
import robo77.domain.TurnResult;
import robo77.domain.card.submitstrategy.HumanSubmitStrategy;
import robo77.domain.player.Player;
import robo77.exception.ExceptionMessage;
import robo77.view.output.DiscordOutput;

public class DiscordCommandListener extends ListenerAdapter {

    private final GameSessionManager gameSessionManager;
    private final DiscordOutput discordOutput;

    public DiscordCommandListener(GameSessionManager gameSessionManager, DiscordOutput discordOutput) {
        this.gameSessionManager = gameSessionManager;
        this.discordOutput = discordOutput;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        try {
            Command.from(event.getName()).execute(this, event);
        } catch (IllegalArgumentException e) {
            discordOutput.showError(event, ExceptionMessage.COMMAND_NOT_FOUND.getMessage());
        }
    }

    public void handleGuide(SlashCommandInteractionEvent event) {
        discordOutput.showGameGuide(event);
    }

    public void handleStartGame(SlashCommandInteractionEvent event) {
        String channelId = event.getChannel().getId();
        if (gameSessionManager.findExistingGame(channelId) != null) {
            discordOutput.showError(event, ExceptionMessage.GAME_ALREADY_EXISTS.getMessage());
            return;
        }
        String playerName = event.getUser().getName();
        RoboGame roboGame = gameSessionManager.startGame(channelId, playerName);
        discordOutput.showGameStart(event, roboGame);
    }

    public void handleHand(SlashCommandInteractionEvent event) {
        findAndExecuteGameAction(event, game
                -> discordOutput.showHand(event, game.getCurrentPlayer()));
    }

    public void handleQuit(SlashCommandInteractionEvent event) {
        findAndExecuteGameAction(event, game -> {
            gameSessionManager.endGame(event.getChannel().getId());
            discordOutput.showGameQuit(event);
        });
    }

    public void handlePlay(SlashCommandInteractionEvent event) {
        findAndExecuteGameAction(event, game -> {
            String cardValue = event.getOption("card").getAsString();
            event.deferReply().queue(hook -> {
                try {
                    playTurns(hook, game, event.getChannel().getId(), cardValue);
                } catch (IllegalArgumentException illegalArgumentException) {
                    discordOutput.showError(event, illegalArgumentException.getMessage());
                }
            });
        });
    }

    private void playTurns(InteractionHook hook, RoboGame game, String channelId, String cardValue) {
        TurnResult playerResult = game.playTurn(new HumanSubmitStrategy(cardValue));
        discordOutput.showSubmittedCard(hook, playerResult);
        discordOutput.showNewCard(hook, playerResult);
        if (handleGameOverIfNeeded(hook, game, channelId, playerResult)) {
            return;
        }
        processBotTurns(hook, game, channelId);
    }

    private boolean handleGameOverIfNeeded(InteractionHook hook, RoboGame game, String channelId, TurnResult result) {
        if (!result.isGameOver()) {
            return false;
        }
        Player winner = game.getWinner();
        discordOutput.showWinner(hook, game.getCurrentScore(), winner.getName());
        gameSessionManager.endGame(channelId);
        return true;
    }

    private void processBotTurns(InteractionHook hook, RoboGame game, String channelId) {
        List<TurnResult> botResults = game.playBotTurns();
        for (TurnResult botResult : botResults) {
            discordOutput.showSubmittedCard(hook, botResult);
            if (handleGameOverIfNeeded(hook, game, channelId, botResult)) {
                break;
            }
        }
    }

    private void findAndExecuteGameAction(SlashCommandInteractionEvent event, Consumer<RoboGame> gameAction) {
        String channelId = event.getChannel().getId();
        RoboGame game = gameSessionManager.findExistingGame(channelId);
        if (game == null) {
            discordOutput.showError(event, ExceptionMessage.NO_GAME_IN_PROGRESS.getMessage());
            return;
        }
        gameAction.accept(game);
    }
}
