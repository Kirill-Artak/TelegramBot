package com.company.commands.telegramcommands;

import com.company.commands.commandhandler.statemachines.IStateMachine;
import org.telegram.abilitybots.api.db.DBContext;

public class TelegramGetCardCommand extends TelegramBaseMachine{
    public TelegramGetCardCommand(DBContext telegramDB, IStateMachine stateMachine) {
        super(
                "getCard",
                "Выберите свою карту",
                telegramDB, stateMachine);
    }
}
