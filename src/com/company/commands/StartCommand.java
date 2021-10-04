package com.company.commands;

import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.sender.SilentSender;

public class StartCommand extends ServiceCommand {
    public StartCommand(SilentSender silent, CommandHandler commandHandler){
        super(silent, commandHandler);
    }

    @Override
    public Ability executeCommand(){
        return Ability.builder()
                .name("start")
                .info("Начало работы")
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send(commandHandler.commandsInfo.get("start"), ctx.chatId()))
                .post(ctx -> {})
                .build();
    }
}
