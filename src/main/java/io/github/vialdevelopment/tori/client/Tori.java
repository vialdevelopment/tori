package io.github.vialdevelopment.tori.client;

import io.github.vialdevelopment.attendance.EventManager;
import io.github.vialdevelopment.tori.api.runnable.module.Module;
import io.github.vialdevelopment.tori.client.gui.ClickGUIScreen;
import io.github.vialdevelopment.tori.client.management.CommandManager;
import io.github.vialdevelopment.tori.client.management.ConfigManager;
import io.github.vialdevelopment.tori.client.management.KeyBindingManager;
import io.github.vialdevelopment.tori.client.management.ModuleManager;
import io.github.vialdevelopment.tori.util.Logger;
import net.minecraft.text.TranslatableText;

public class Tori {
    public static final Tori INSTANCE = new Tori();

    public static final String MOD_NAME = "Tori";

    public final EventManager eventManager = new EventManager();

    public final CommandManager commandManager = new CommandManager();

    public final ModuleManager moduleManager = new ModuleManager();

    public final KeyBindingManager keyBindingManager = new KeyBindingManager();

    public final ConfigManager configManager = new ConfigManager();

    public final ClickGUIScreen clickGUIScreen = new ClickGUIScreen(new TranslatableText("ToriClickGUI"));

    public void init() {
        this.commandManager.init();
        Logger.log("Command Manager");

        this.moduleManager.init();
        Logger.log("Module Manager");

        this.keyBindingManager.init();
        Logger.log("Bindings!");

        this.configManager.init();
        this.configManager.readConfig();

        for (Module module : this.moduleManager.getModules()) {
            Logger.log(module.getName());
        }
        this.clickGUIScreen.initGUI();
    }
}
