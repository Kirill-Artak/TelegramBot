package com.company;

import com.company.commands.commandhandler.CommandHandler;
import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.ICommandStore;
import com.company.commands.commandhandler.CommandStore;
import com.company.database.IDatabaseCore;
import com.company.database.mongoDB.MongoDBCore;
import com.company.repositories.CardRepository;
import com.company.repositories.ICardRepository;
import com.company.repositories.IUserRepository;
import com.company.repositories.UserRepository;
import org.bson.Document;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> envVars = System.getenv();
        //IDatabaseWrapper<?, ?> db = new MongoDBSyncWrapper(envVars.get("MONGODB"), envVars.get("DB_NAME"));

        IDatabaseCore<Document> db = new MongoDBCore(envVars.get("MONGODB"), envVars.get("DB_NAME"));
        IUserRepository userRepository = new UserRepository(db);
        ICardRepository cardRepository = new CardRepository(db);

        ICommandHandler commandHandler = new CommandHandler(userRepository, cardRepository);
        ICommandStore commandStore = new CommandStore(commandHandler);


        TelegramBot telegramBot = new TelegramBot(
                envVars.get("BOT_TOKEN"),
                envVars.get("BOT_USERNAME"),
                commandStore);
        telegramBot.botStart();
    }
}
