package io.github.vialdevelopment.tori.client;

import io.github.vialdevelopment.attendance.EventManager;
import io.github.vialdevelopment.tori.client.management.CommandManager;
import io.github.vialdevelopment.tori.client.management.ModuleManager;
import io.github.vialdevelopment.tori.util.Logger;

public class Tori {
    public static Tori INSTANCE = new Tori();

    public final String MOD_NAME = "Tori";

    public final EventManager eventManager = new EventManager();

    public final CommandManager commandManager = new CommandManager();

    public final ModuleManager moduleManager = new ModuleManager();

    public void init() {
        this.commandManager.init();
        Logger.log("Command Manager");

        this.moduleManager.init();
        Logger.log("Module Manager");
    }
}
