package robo77.discord;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.function.Consumer;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import robo77.domain.RoboGame;
import robo77.domain.TurnResult;
import robo77.domain.card.submitstrategy.HumanSubmitStrategy;
import robo77.domain.player.Player;
import robo77.exception.ExceptionMessage;
import robo77.view.output.DiscordGameOutput;

@ExtendWith(MockitoExtension.class)
class GameCommandListenerTest {

    @Mock
    private GameSessionManager gameSessionManager;

    @Mock
    private DiscordGameOutput discordGameOutput;

    @Mock
    private SlashCommandInteractionEvent event;

    @Mock
    private MessageChannelUnion channel;

    @Mock
    private User user;

    @Mock
    private RoboGame game;

    @Mock
    private ReplyCallbackAction replyAction;

    @Mock
    private InteractionHook hook;

    @Mock
    private OptionMapping optionMapping;

    private GameCommandListener listener;

    private static final String CHANNEL_ID = "123456789";
    private static final String PLAYER_NAME = "TestPlayer";

    @BeforeEach
    void setUp() {
        listener = new GameCommandListener(gameSessionManager, discordGameOutput);
    }

    private void setupBasicEventMocks() {
        when(event.getChannel()).thenReturn(channel);
        when(channel.getId()).thenReturn(CHANNEL_ID);
    }

    private void setupUserMocks() {
        when(event.getUser()).thenReturn(user);
        when(user.getName()).thenReturn(PLAYER_NAME);
    }

    @Test
    void 명령어가_존재하지_않으면_예외를_반환한다() {
        // given
        when(event.getName()).thenReturn("unknown");
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(discordGameOutput).showError(event, ExceptionMessage.COMMAND_NOT_FOUND.getMessage());
    }

    @Test
    void 게임을_시작하는_명령어를_수헹한다() {
        // given
        setupBasicEventMocks();
        setupUserMocks();
        when(event.getName()).thenReturn("startgame");
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(null);
        when(gameSessionManager.startGame(CHANNEL_ID, PLAYER_NAME)).thenReturn(game);
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(gameSessionManager).startGame(CHANNEL_ID, PLAYER_NAME);
        verify(discordGameOutput).showGameStart(event, game);
        verify(discordGameOutput, never()).showError(any(), anyString());
    }

    @Test
    void 이미_진행중인_게임이_있으면_게임을_시작할_수_없다() {
        // given
        setupBasicEventMocks();
        when(event.getName()).thenReturn("startgame");
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(game);
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(gameSessionManager, never()).startGame(anyString(), anyString());
        verify(discordGameOutput).showError(event, ExceptionMessage.GAME_ALREADY_EXISTS.getMessage());
    }

    @Test
    void 핸드_조회하는_명령어를_수헹한다() {
        // given
        setupBasicEventMocks();
        when(event.getName()).thenReturn("hand");
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(game);
        Player currentPlayer = mock(Player.class);
        when(game.getCurrentPlayer()).thenReturn(currentPlayer);
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(discordGameOutput).showHand(event, currentPlayer);
    }

    @Test
    void 진행중인_게임이_없으면_핸드를_조회할_수_없다() {
        // given
        setupBasicEventMocks();
        when(event.getName()).thenReturn("hand");
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(null);
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(discordGameOutput).showError(event, ExceptionMessage.NO_GAME_IN_PROGRESS.getMessage());
    }

    @Test
    void 게임을_종료하는_명령어를_수헹한다() {
        // given
        setupBasicEventMocks();
        when(event.getName()).thenReturn("quit");
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(game);
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(gameSessionManager).endGame(CHANNEL_ID);
        verify(discordGameOutput).showGameQuit(event);
    }

    @Test
    void 카드를_제출할_수_있다() {
        // given
        setupBasicEventMocks();
        when(event.getName()).thenReturn("play");
        when(event.getOption("card")).thenReturn(optionMapping);
        when(optionMapping.getAsString()).thenReturn("5");
        when(event.deferReply()).thenReturn(replyAction);
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(game);

        TurnResult playerResult = mock(TurnResult.class);
        when(playerResult.isGameOver()).thenReturn(false);
        when(game.playTurn(any())).thenReturn(playerResult);
        when(game.playBotTurns()).thenReturn(Arrays.asList());

        doAnswer(invocation -> {
            Consumer<InteractionHook> callback = invocation.getArgument(0);
            callback.accept(hook);
            return null;
        }).when(replyAction).queue(any());
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(game).playTurn(any(HumanSubmitStrategy.class));
        verify(discordGameOutput).showSubmittedCard(hook, playerResult);
        verify(discordGameOutput).showNewCard(hook, playerResult);
        verify(game).playBotTurns();
    }

    @Test
    void 카드_제출_후_게임이_종료된다() {
        // given
        setupBasicEventMocks();
        when(event.getName()).thenReturn("play");
        when(event.getOption("card")).thenReturn(optionMapping);
        when(optionMapping.getAsString()).thenReturn("5");
        when(event.deferReply()).thenReturn(replyAction);
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(game);

        TurnResult playerResult = mock(TurnResult.class);
        when(playerResult.isGameOver()).thenReturn(true);
        when(game.playTurn(any())).thenReturn(playerResult);

        Player winner = mock(Player.class);
        when(winner.getName()).thenReturn("bot");
        when(game.getWinner()).thenReturn(winner);
        when(game.getCurrentScore()).thenReturn(100);

        doAnswer(invocation -> {
            Consumer<InteractionHook> callback = invocation.getArgument(0);
            callback.accept(hook);
            return null;
        }).when(replyAction).queue(any());
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(discordGameOutput).showWinner(hook, 100, "bot");
        verify(gameSessionManager).endGame(CHANNEL_ID);
        verify(game, never()).playBotTurns();
    }

    @Test
    void 봇이_카드를_제출하면_게임이_종료된다() {
        // given
        setupBasicEventMocks();
        when(event.getName()).thenReturn("play");
        when(event.getOption("card")).thenReturn(optionMapping);
        when(optionMapping.getAsString()).thenReturn("5");
        when(event.deferReply()).thenReturn(replyAction);
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(game);

        TurnResult playerResult = mock(TurnResult.class);
        when(playerResult.isGameOver()).thenReturn(false);

        TurnResult botResult = mock(TurnResult.class);
        when(botResult.isGameOver()).thenReturn(true);

        when(game.playTurn(any())).thenReturn(playerResult);
        when(game.playBotTurns()).thenReturn(Arrays.asList(botResult));

        Player winner = mock(Player.class);
        when(winner.getName()).thenReturn(PLAYER_NAME);
        when(game.getWinner()).thenReturn(winner);
        when(game.getCurrentScore()).thenReturn(55);

        doAnswer(invocation -> {
            Consumer<InteractionHook> callback = invocation.getArgument(0);
            callback.accept(hook);
            return null;
        }).when(replyAction).queue(any());
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(game).playBotTurns();
        verify(discordGameOutput).showSubmittedCard(hook, botResult);
        verify(discordGameOutput).showWinner(hook, 55, PLAYER_NAME);
        verify(gameSessionManager).endGame(CHANNEL_ID);
    }

    @Test
    void 손패에_없는_카드를_제출하면_예외가_발생한다() {
        // given
        setupBasicEventMocks();
        when(event.getName()).thenReturn("play");
        when(event.getOption("card")).thenReturn(optionMapping);
        when(optionMapping.getAsString()).thenReturn("invalid");
        when(event.deferReply()).thenReturn(replyAction);
        when(gameSessionManager.findExistingGame(CHANNEL_ID)).thenReturn(game);
        when(game.playTurn(any())).thenThrow(new IllegalArgumentException(ExceptionMessage.INVALID_CARD.getMessage()));

        doAnswer(invocation -> {
            Consumer<InteractionHook> callback = invocation.getArgument(0);
            callback.accept(hook);
            return null;
        }).when(replyAction).queue(any());
        // when
        listener.onSlashCommandInteraction(event);
        // then
        verify(discordGameOutput).showError(event, ExceptionMessage.INVALID_CARD.getMessage());
    }
}
