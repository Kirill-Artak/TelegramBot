package com.company.commands.telegramcommands;

import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.statemachines.IStateMachine;
import org.mapdb.DB;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.MapDBContext;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.function.Consumer;

public class TelegramAddCommand extends TelegramBaseMachine {
    public TelegramAddCommand(DBContext telegramDB, IStateMachine stateMachine){
        super(
                "add",
                "Добавляет карточку в ваш список",
                telegramDB,
                stateMachine
        );
    }
}
