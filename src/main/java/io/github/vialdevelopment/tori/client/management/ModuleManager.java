package io.github.vialdevelopment.tori.client.management;

import io.github.vialdevelopment.tori.api.management.IRunnableManager;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.api.traits.TraitRunnable;
import io.github.vialdevelopment.tori.api.runnable.module.Module;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.modules.exploit.ItemTweaksModule;
import io.github.vialdevelopment.tori.client.modules.exploit.SpeedMineModule;
import io.github.vialdevelopment.tori.client.modules.misc.PortalChatModule;
import io.github.vialdevelopment.tori.client.modules.movement.ElytraFlyModule;
import io.github.vialdevelopment.tori.client.modules.movement.JesusModule;
import io.github.vialdevelopment.tori.client.modules.movement.SprintModule;
import io.github.vialdevelopment.tori.client.modules.movement.VelocityModule;
import io.github.vialdevelopment.tori.client.modules.player.FreeCamModule;
import io.github.vialdevelopment.tori.client.modules.player.MultiSessionModule;
import io.github.vialdevelopment.tori.client.modules.render.*;
import io.github.vialdevelopment.tori.util.Logger;

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
        this.addRunnable(new ElytraFlyModule());
        this.addRunnable(new VelocityModule());
        this.addRunnable(new PortalChatModule());
        this.addRunnable(new NoRenderModule());
        this.addRunnable(new HUDModule());
        this.addRunnable(ClickGUIModule.INSTANCE);
        this.addRunnable(JesusModule.INSTANCE);
        this.addRunnable(new MultiSessionModule());
        this.addRunnable(new ESPModule());
        this.addRunnable(new SpeedMineModule());
    }

    @Override
    public void addRunnable(TraitRunnable runnable) {
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

        if (module.shouldAttend()) Tori.INSTANCE.eventManager.registerAttender(module);

        this.modules.add(module);
        Logger.log(module.getName());
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

    public Module getModule(String name) {
        for (Module module : this.getModules()) {
            if (module.getName().equalsIgnoreCase(name)) return module;
        }
        return null;
    }

    // getters and setters

    public List<Module> getModules() {
        return this.modules;
    }
}
