package com.company.commands.telegramcommands;

import com.company.commands.BaseCommand;
import com.company.commands.commandhandler.messagecontext.TelegramMessageContext;
import com.company.commands.commandhandler.statemachines.IState;
import com.company.commands.commandhandler.statemachines.IStateMachine;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class TelegramBaseMachine extends BaseCommand implements AbilityExtension{
    private final DBContext telegramDB;
    private final IStateMachine stateMachine;

    protected TelegramBaseMachine(String name,
                                  String description,
                                  DBContext telegramDB,
                                  IStateMachine stateMachine) {
        super(name, description);
        this.telegramDB = telegramDB;
        this.stateMachine = stateMachine;
    }

    public Ability addCard() {
        IState state = stateMachine.getFirst();

        ReplyFlow.ReplyFlowBuilder builder = ReplyFlow.builder(telegramDB, state.id());

        ReplyFlow reply = buildStateMachine(state, builder, true).build();

        return Ability.builder()
                .name(getName())
                .info(getDescription())
                .reply(reply)
                .input(0)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .action(c -> {})
                .post(c -> {})
                .build();
    }

    private ReplyFlow.ReplyFlowBuilder buildStateMachine(IState first,
                                                         ReplyFlow.ReplyFlowBuilder builder,
                                                         boolean isFirst){
        builder.action((baseAbilityBot, update) -> {
            var ctx = new TelegramMessageContext(
                    baseAbilityBot,
                    update);

            first.action(ctx);
        });

        if(isFirst){
            builder.onlyIf(update -> first.predicate(
                    new TelegramMessageContext(update)));
        }
        else{
            builder.onlyIf(update -> !update.getMessage().isCommand() && first.predicate(
                    new TelegramMessageContext(update)));
        }



        if (first.getNext().isEmpty())
            return builder;

        for (IState state: first.getNext()) {
            builder.next(buildStateMachine(state, ReplyFlow.builder(telegramDB, state.id()), false).build());
        }

        return builder;
    }

    /*
    private ReplyFlow.ReplyFlowBuilder buildStateMachine(IState first, ReplyFlow.ReplyFlowBuilder builder){
        builder.action((baseAbilityBot, update) -> first.action(
                new TelegramMessageContext(
                        baseAbilityBot,
                        update)
        ));

        builder.onlyIf(upd -> first.predicate(new TelegramMessageContext(upd)));

        for (IState state: first.getNext()) {
            builder.next(Reply.of()
        }
    }

     */
}
