package robo77;

import net.dv8tion.jda.api.JDA;
import robo77.discord.CommandRegistrar;
import robo77.discord.GameSessionManager;
import robo77.discord.JDAInitializer;
import robo77.discord.config.DiscordConfig;
import robo77.view.output.DiscordGameOutput;

public class DiscordBotApplication {
    public static void main(String[] args) throws InterruptedException {
        String token = DiscordConfig.getBotToken();
        GameSessionManager sessionManager = new GameSessionManager();
        DiscordGameOutput discordGameOutput = new DiscordGameOutput();
        JDAInitializer initializer = new JDAInitializer();
        JDA jda = initializer.initialize(token, sessionManager, discordGameOutput);
        CommandRegistrar registrar = new CommandRegistrar();
        registrar.register(jda);
    }
}
