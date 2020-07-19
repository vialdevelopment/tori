package io.github.vialdevelopment.tori.client.management;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.Module;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.TickEvent;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;

import java.util.ArrayList;
import java.util.List;

public class KeyBindingManager {

    private final List<KeyBinding> keyBindings = new ArrayList<>();

    public void init() {
        this.addAllKeyBindings();
        for (KeyBinding keyBinding : this.getKeyBindings()) {
            KeyBindingHelper.registerKeyBinding(keyBinding);
        }

        Tori.INSTANCE.eventManager.registerAttender(this);
        Tori.INSTANCE.eventManager.setAttending(this, true);
    }

    @Attend
    private final Attender<TickEvent> tickEvent = new Attender<>(TickEvent.class, event -> {
       for (Module module : Tori.INSTANCE.moduleManager.getModules()) {
           if (module.getKeyBind().wasPressed()) {
               module.toggle();
           }
       }
    });

    private void addAllKeyBindings() {
        this.addModuleBinds();
    }

    private void addModuleBinds() {
        for (Module module : Tori.INSTANCE.moduleManager.getModules()) {
            this.getKeyBindings().add(module.getKeyBind());
        }
    }

    // Getter

    public List<KeyBinding> getKeyBindings() {
        return this.keyBindings;
    }
}
