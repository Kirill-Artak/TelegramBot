package com.company;

import com.company.commands.commandhandler.CommandHandler;
import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.ICommandStore;
import com.company.commands.commandhandler.CommandStore;
import com.company.database.IDatabaseWrapper;
import com.company.database.mongoDB.MongoDBSyncWrapper;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> envVars = System.getenv();
        IDatabaseWrapper db = new MongoDBSyncWrapper(envVars.get("MONGODB"), envVars.get("DB_NAME"));
        ICommandHandler commandHandler = new CommandHandler(db);
        ICommandStore commandStore = new CommandStore(commandHandler);


        TelegramBot telegramBot = new TelegramBot(
                envVars.get("BOT_TOKEN"),
                envVars.get("BOT_USERNAME"),
                commandStore);
        telegramBot.botStart();
    }
}
