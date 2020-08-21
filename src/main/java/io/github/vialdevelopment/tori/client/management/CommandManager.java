package io.github.vialdevelopment.tori.client.management;

import io.github.vialdevelopment.tori.api.management.IRunnableManager;
import io.github.vialdevelopment.tori.api.traits.TraitRunnable;
import io.github.vialdevelopment.tori.api.runnable.command.Command;
import io.github.vialdevelopment.tori.client.commands.CommandsCommand;
import io.github.vialdevelopment.tori.client.commands.LoginCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements IRunnableManager {

    private final List<Command> commands = new ArrayList<>();

    public String PREFIX = ".";

    @Override
    public void addRunnables() {
        this.addRunnable(new CommandsCommand());
        this.addRunnable(new LoginCommand());
    }

    @Override
    public void addRunnable(TraitRunnable runnable) {
        this.getCommands().add((Command) runnable);
    }

    @Override
    public boolean dispatchRunnable(String message) {
        final String[] command = message.split(" ");
        for (Command possibleCommand : this.getCommands()) {
            if (command[0].equalsIgnoreCase(possibleCommand.getName())) {
                final String[] commandArgs = message.replace(command[0] + " ", "").split(" ");
                possibleCommand.run(commandArgs);
                return true;
            }
        }
        return false;
    }

    // getters and setters

    public List<Command> getCommands() {
        return this.commands;
    }
}
