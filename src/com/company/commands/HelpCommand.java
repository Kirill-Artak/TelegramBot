package com.company.commands;

import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.sender.SilentSender;

public class HelpCommand extends ServiceCommand {
    public HelpCommand(SilentSender silent, CommandHandler commandHandler){
        super(silent, commandHandler);
    }

    @Override
    public Ability executeCommand() {
        return Ability.builder()
                .name("help")
                .info("Показывает возможности бота")
                .input(0)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .action(ctx -> silent.send(commandHandler.commandsInfo.get("help"), ctx.chatId()))
                .build();
    }
}
