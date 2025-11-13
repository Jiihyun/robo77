package robo77;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import robo77.discord.Command;
import robo77.discord.GameCommandListener;
import robo77.service.GameSessionManager;

public class DiscordBotApplication {

    public static void main(String[] args) throws InterruptedException {
        String botToken = System.getenv("DISCORD_BOT_TOKEN");
        if (botToken == null || botToken.isBlank()) {
            System.out.println("환경 변수에서 디스코드 봇 토큰을 찾을 수 없습니다. (DISCORD_BOT_TOKEN)");
            return;
        }

        GameSessionManager gameSessionManager = new GameSessionManager();

        JDA jda = JDABuilder.createDefault(botToken)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT) // 메시지 내용을 읽기 위한 Intent
                .setActivity(Activity.playing("로보77")) // 봇의 '플레이 중' 상태 설정
                .addEventListeners(new GameCommandListener(gameSessionManager)) // 명령어 리스너 추가
                .build()
                .awaitReady(); // 봇이 완전히 준비될 때까지 대기

        for (Command command : Command.values()) {
            jda.upsertCommand(command.getCommand(), command.getDescription()).queue();
        }
        
        System.out.println("==================================================");
        System.out.println("로보77 디스코드 봇이 성공적으로 시작되었습니다!");
        System.out.println("==================================================");
    }
}
