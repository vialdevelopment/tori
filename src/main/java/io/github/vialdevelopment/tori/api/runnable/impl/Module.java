package io.github.vialdevelopment.tori.api.runnable.impl;

import io.github.vialdevelopment.tori.api.runnable.toggleable.IToggleable;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.util.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.List;

public class Module extends Command implements IToggleable {

    private boolean state;

    private int keyBind = InputUtil.UNKNOWN_KEY.getCode();

    private Category category;

    private final List<Setting> settings = new ArrayList<>();

    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    public Module(String name, String description, Category category) {
        super(name, description);
        this.category = category;
    }

    @Override
    public boolean getState() {
        return this.state;
    }

    @Override
    public void setState(boolean state) {
        if (state) {
            this.onEnable();
        } else {
            this.onDisable();
        }
        this.state = state;
    }

    @Override
    public void onEnable() {
        if (!this.state) {
            Tori.INSTANCE.eventManager.setAttending(this, true);
        }

    }

    @Override
    public void onDisable() {
        if (this.state) {
            Tori.INSTANCE.eventManager.setAttending(this, false);
        }
    }

    @Override
    public void run(String[] args) {
        if (args[0].equalsIgnoreCase("enabled")) {
            if (args.length == 2) {
                this.setState(Boolean.parseBoolean(args[1]));
            } else {
                this.toggle();
            }
            return;
        }

        if (args[0].equalsIgnoreCase("bind")) {

            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("none")) {
                    this.keyBind = InputUtil.UNKNOWN_KEY.getCode();
                }
                try {
                    this.keyBind = InputUtil.fromTranslationKey("key.keyboard." + args[1].toLowerCase()).getCode();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            return;
        }

        for (Setting setting : settings) {
            if (args[0].equalsIgnoreCase(setting.getName())) {
                if (setting.getValue() instanceof Boolean) {
                    setting.setValue(Boolean.parseBoolean(args[1]));
                } else
                if (setting.getValue() instanceof Number) {
                    if (setting.getValue() instanceof Double) {
                        setting.setValue(Double.parseDouble(args[1]));
                    } else
                    if (setting.getValue() instanceof Float) {
                        setting.setValue(Float.parseFloat(args[1]));
                    } else
                    if (setting.getValue() instanceof Integer) {
                        setting.setValue(Integer.parseInt(args[1]));
                    } else
                    if (setting.getValue() instanceof Long) {
                        setting.setValue(Long.parseLong(args[1]));
                    }
                }
                if (setting.getValue() instanceof String) {
                    setting.setValue(args[1]);
                }
            }
        }
    }

    // getters and setters

    public Category getCategory() {
        return this.category;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public int getKeyBind() {
        return this.keyBind;
    }
}
