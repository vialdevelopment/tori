package io.github.vialdevelopment.tori.client.config;

import io.github.vialdevelopment.tori.api.config.Configurable;
import io.github.vialdevelopment.tori.api.runnable.impl.Module;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.util.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ModuleConfig implements Configurable {

    @Override
    public void write() {
        List<String> lines = new ArrayList<>();
        Path file = Paths.get(this.getPath());

        // For all of the modules, write em
        for (Module module : Tori.INSTANCE.moduleManager.getModules()) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(module.getName() + ":" + module.getState() + this.getDivider());
            if (module.getSettings() != null) {
                // Settings!
                for (Setting setting : module.getSettings()) {
                    stringBuilder.append(setting.getName() + ":" + setting.getValue() + this.getDivider());
                }
            }
            lines.add(stringBuilder.toString());
        }

        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read() {
        File file = new File(this.getPath());

        if (!file.exists()) {
            // If the file doesn't exist, create it
            file.mkdir();
        }

        try {
            // for all the lines in a file, add it to the list
            BufferedReader br = new BufferedReader(new FileReader(file));
            String string;
            while ((string = br.readLine()) != null) {
                final String[] args = string.split(this.getDivider());
                final String[] moduleArgs = args[0].split(":");
                final Module module = Tori.INSTANCE.moduleManager.getModule(moduleArgs[0]);

                if (module == null) continue;

                // Set module state
                module.setState(Boolean.parseBoolean(moduleArgs[1]));

                if (args.length > 2) {
                    for (String arg : args) {
                        final String[] settingArgs = arg.split(":");

                        final Setting setting = module.getSetting(settingArgs[0]);

                        if (setting == null) continue;

                        if (setting.getValue() instanceof Boolean) {
                            setting.setValue(Boolean.parseBoolean(settingArgs[1]));
                        }
                        if (setting.getValue() instanceof Number) {
                            if (setting.getValue() instanceof Integer) {
                                setting.setValue(Integer.parseInt(settingArgs[1]));
                            }
                            if (setting.getValue() instanceof Float) {
                                setting.setValue(Float.parseFloat(settingArgs[1]));
                            }
                            if (setting.getValue() instanceof Long) {
                                setting.setValue(Long.parseLong(settingArgs[1]));
                            }
                            if (setting.getValue() instanceof Double) {
                                setting.setValue(Double.parseDouble(settingArgs[1]));
                            }
                        }
                        if (setting.getValue() instanceof String) {
                            setting.setValue(settingArgs[1]);
                        }
                    }
                }
            }
        } catch (IOException ignored) {
            Logger.log("File Not found");
        }

    }

    @Override
    public String getPath() {
        return Tori.INSTANCE.configManager.PATH + "modules.txt";
    }
}
