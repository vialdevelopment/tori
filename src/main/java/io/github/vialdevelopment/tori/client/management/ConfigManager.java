package io.github.vialdevelopment.tori.client.management;

import io.github.vialdevelopment.tori.api.config.TraitConfigurable;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.config.ModuleConfig;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class ConfigManager {
    public final String PATH = MinecraftClient.getInstance().runDirectory + File.separator + Tori.MOD_NAME + File.separator;

    private final List<TraitConfigurable> configs = new ArrayList<>();

    public void init() {
        this.addConfigs();
    }

    private void addConfigs() {
        this.getConfigs().add(new ModuleConfig());
    }

    /**
     * Writes the configuration to Canopy.json
     * Friends and Modules
     */
    public void writeConfig() {

        File directory = new File(this.PATH);

        if (!directory.exists()) directory.mkdir();

        for (TraitConfigurable config : this.getConfigs()) {
            config.write();
        }
    }

    /**
     * Loads the modules from the json
     */
    public void readConfig() {
        for (TraitConfigurable config : this.getConfigs()) {
            config.read();
        }
    }

    // Getters

    public List<TraitConfigurable> getConfigs() {
        return this.configs;
    }
}
