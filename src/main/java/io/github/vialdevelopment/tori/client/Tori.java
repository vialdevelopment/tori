package io.github.vialdevelopment.tori.client;

import io.github.vialdevelopment.tori.client.management.CommandManager;

public class Tori {
    public static Tori INSTANCE = new Tori();

    public CommandManager commandManager = new CommandManager();

    public void init() {
        this.commandManager.init();
    }
}
