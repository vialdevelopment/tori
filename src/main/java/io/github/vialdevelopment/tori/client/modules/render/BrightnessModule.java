package io.github.vialdevelopment.tori.client.modules.render;

import io.github.vialdevelopment.tori.api.runnable.module.Category;
import io.github.vialdevelopment.tori.api.runnable.module.Module;

public class BrightnessModule extends Module {

    public static final BrightnessModule INSTANCE = new BrightnessModule();

    private BrightnessModule() {
        super("Brightness", "Brightens up your game, making playing a bit easier", Category.RENDER);
    }

    @Override
    public boolean shouldAttend() {
        return false;
    }
}
