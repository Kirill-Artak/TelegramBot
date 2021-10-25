package com.company;

import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.TelegramCommandHandler;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> envVars = System.getenv();
        ICommandHandler<AbilityExtension> commandHandler = new TelegramCommandHandler();

        TelegramBot telegramBot = new TelegramBot(envVars.get("BOT_TOKEN"), envVars.get("BOT_USERNAME"), commandHandler);
        telegramBot.botStart();
    }
}
