package io.github.vialdevelopment.tori;

import io.github.vialdevelopment.tori.client.Tori;
import net.fabricmc.api.ModInitializer;

public class ToriEntryPoint implements ModInitializer {
    @Override
    public void onInitialize() {
        Tori.INSTANCE.init();
    }
}
