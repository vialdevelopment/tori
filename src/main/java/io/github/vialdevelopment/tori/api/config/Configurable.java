package io.github.vialdevelopment.tori.api.config;

import io.github.vialdevelopment.tori.client.Tori;
import net.minecraft.client.MinecraftClient;

import java.io.File;

public interface Configurable {

    default String getPath() {
        return Tori.INSTANCE.configManager.PATH;
    }

    default String getDivider() {
        return ", ";
    }

    void write();

    void read();
}
