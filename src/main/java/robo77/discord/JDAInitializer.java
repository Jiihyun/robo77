package robo77.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class JDAInitializer {

    private static final String ACTIVITY_MESSAGE = "로보77";

    public JDA initialize(GameSessionManager sessionManager, String botToken) throws InterruptedException {
        return JDABuilder.createDefault(botToken)
                .setActivity(Activity.playing(ACTIVITY_MESSAGE))
                .addEventListeners(new GameCommandListener(sessionManager))
                .build()
                .awaitReady();
    }
}
