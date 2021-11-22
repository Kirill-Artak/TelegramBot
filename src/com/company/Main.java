package com.company;

import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.TelegramCommandHandler;
import com.company.database.IDatabaseWrapper;
import com.company.database.mongoDB.MongoDBSyncWrapper;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> envVars = System.getenv();
        ICommandHandler<AbilityExtension> commandHandler = new TelegramCommandHandler();
        IDatabaseWrapper db = new MongoDBSyncWrapper(envVars.get("MONGODB"), envVars.get("DB_NAME"));

        TelegramBot telegramBot = new TelegramBot(
                envVars.get("BOT_TOKEN"),
                envVars.get("BOT_USERNAME"),
                commandHandler,
                db);
        telegramBot.botStart();
    }
}
