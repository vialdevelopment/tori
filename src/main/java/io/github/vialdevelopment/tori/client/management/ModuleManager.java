package io.github.vialdevelopment.tori.client.management;

import io.github.vialdevelopment.tori.api.management.IRunnableManager;
import io.github.vialdevelopment.tori.api.runnable.IRunnable;
import io.github.vialdevelopment.tori.api.runnable.impl.Command;
import io.github.vialdevelopment.tori.api.runnable.impl.Module;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.modules.exploit.ItemTweaksModule;
import io.github.vialdevelopment.tori.client.modules.movement.SprintModule;
import io.github.vialdevelopment.tori.client.modules.player.FreeCamModule;
import io.github.vialdevelopment.tori.client.modules.render.BrightnessModule;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager implements IRunnableManager {

    private final List<Module> modules = new ArrayList<>();

    @Override
    public void addRunnables() {
        this.addRunnable(new SprintModule());
        this.addRunnable(BrightnessModule.INSTANCE);
        this.addRunnable(new ItemTweaksModule());
        this.addRunnable(new FreeCamModule());
    }

    @Override
    public void addRunnable(IRunnable runnable) {
        final Module module = (Module) runnable;
        try {
            for (Field field : module.getClass().getDeclaredFields()) {
                if (Setting.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    Setting value = (Setting) field.get(module);
                    module.getSettings().add(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Tori.INSTANCE.eventManager.registerAttender(module);

        this.getModules().add(module);
    }

    @Override
    public boolean dispatchRunnable(String message) {
        final String[] command = message.split(" ");
        for (Module possibleModule : this.getModules()) {
            if (command[0].equalsIgnoreCase(possibleModule.getName())) {
                final String[] commandArgs = message.replace(command[0] + " ", "").split(" ");
                possibleModule.run(commandArgs);
                return true;
            }
        }
        return false;
    }

    // getters and setters

    public List<Module> getModules() {
        return this.modules;
    }
}
