package io.github.vialdevelopment.tori.client.management;

import io.github.vialdevelopment.tori.api.management.IRunnableManager;
import io.github.vialdevelopment.tori.api.runnable.IRunnable;
import io.github.vialdevelopment.tori.api.runnable.impl.Command;
import io.github.vialdevelopment.tori.api.runnable.impl.Module;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.Tori;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager implements IRunnableManager {

    private final List<Module> modules = new ArrayList<>();

    @Override
    public void addRunnables() {

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
        for (Command possibleCommand : this.getModules()) {
            if (command[0].equalsIgnoreCase(possibleCommand.getName())) {
                final String[] commandArgs = message.replace(command[0] + " ", "").split(" ");
                possibleCommand.run(commandArgs);
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
