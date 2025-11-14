package robo77;

import net.dv8tion.jda.api.JDA;
import robo77.discord.CommandRegistrar;
import robo77.discord.GameSessionManager;
import robo77.discord.JDAInitializer;
import robo77.discord.config.DiscordConfig;

public class DiscordBotApplication {
    public static void main(String[] args) throws InterruptedException {
        String token = DiscordConfig.getBotToken();
        GameSessionManager sessionManager = new GameSessionManager();
        JDAInitializer initializer = new JDAInitializer();
        JDA jda = initializer.initialize(sessionManager, token);
        CommandRegistrar registrar = new CommandRegistrar();
        registrar.register(jda);
    }
}
