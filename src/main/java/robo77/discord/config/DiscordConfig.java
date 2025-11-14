package robo77.discord.config;

import robo77.exception.ExceptionMessage;

public class DiscordConfig {

    public static String getBotToken() {
        String token = System.getenv("DISCORD_BOT_TOKEN");
        validateToken(token);
        return token;
    }

    private static void validateToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalStateException(ExceptionMessage.INVALID_DISCORD_BOT_TOKEN.getMessage());
        }
    }
}
