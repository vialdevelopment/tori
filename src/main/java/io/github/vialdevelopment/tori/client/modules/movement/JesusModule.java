package io.github.vialdevelopment.tori.client.modules.movement;

import io.github.vialdevelopment.tori.api.runnable.impl.module.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Module;

public class JesusModule extends Module {

    public static final JesusModule INSTANCE = new JesusModule();

    public JesusModule() {
        super("Jesus", "Lets you walk on water", Category.MOVEMENT);
    }
}
