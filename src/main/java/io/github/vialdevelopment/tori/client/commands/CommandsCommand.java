package io.github.vialdevelopment.tori.client.commands;

import io.github.vialdevelopment.tori.api.runnable.command.Command;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.util.Logger;

public class CommandsCommand extends Command {
    public CommandsCommand() {
        super("Commands", "Shows all the commands in the client");
    }

    @Override
    public void run(String[] args) {
        for (Command command : Tori.INSTANCE.commandManager.getCommands()) {
            Logger.log(command.getName() + ": " + command.getDescription());
        }
    }
}
