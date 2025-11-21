package robo77.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import robo77.exception.ExceptionMessage;
import robo77.view.output.DiscordOutput;

public class JDAInitializer {

    private static final String ACTIVITY_MESSAGE = "로보77";

    public JDA initialize(String botToken, GameSessionManager sessionManager, DiscordOutput discordOutput) {
        try {
            return JDABuilder.createDefault(botToken)
                    .setActivity(Activity.playing(ACTIVITY_MESSAGE))
                    .addEventListeners(new DiscordCommandListener(sessionManager, discordOutput))
                    .build()
                    .awaitReady();
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(ExceptionMessage.JDA_INIT_INTERRUPTED.getMessage(), interruptedException);
        }
    }
}
