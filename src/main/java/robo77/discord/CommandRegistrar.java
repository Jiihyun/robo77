package robo77.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

public class CommandRegistrar {

    public void register(JDA jda) {
        for (Command command : Command.values()) {
            CommandCreateAction commandCreateAction = jda.upsertCommand(command.getCommand(), command.getDescription());
            if (command == Command.PLAY) {
                commandCreateAction.addOption(OptionType.STRING, "card", "내고 싶은 카드 (예: 3, -10, x2, reverse)", true);
            }
            commandCreateAction.queue();
        }
    }
}
