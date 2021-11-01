package com.company.commands.telegramcommands;

import com.company.commands.BaseCommand;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.function.Consumer;

public abstract class TelegramBaseCommand extends BaseCommand implements AbilityExtension {
    protected final Consumer<MessageContext> action;
    protected final Consumer<MessageContext> postAction;

    protected TelegramBaseCommand(String name,
                                  String description,
                                  Consumer<MessageContext> action,
                                  Consumer<MessageContext> postAction) {
        super(name, description);
        this.action = action;
        this.postAction = postAction;
    }

    public Ability executeCommand() {
        return Ability.builder()
                .name(getName())
                .info(getDescription())
                .input(0)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .action(action)
                .post(postAction)
                .build();
    }
}
