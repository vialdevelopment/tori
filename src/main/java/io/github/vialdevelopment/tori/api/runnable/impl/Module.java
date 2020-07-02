package io.github.vialdevelopment.tori.api.runnable.impl;

import io.github.vialdevelopment.tori.api.runnable.toggleable.IToggleable;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.Tori;

import java.util.ArrayList;
import java.util.List;

public class Module extends Command implements IToggleable {

    private boolean state;

    private Category category;

    private final List<Setting> settings = new ArrayList<>();

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
        this.state = state;
        if (state) {
            this.onEnable();
        } else {
            this.onDisable();
        }
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

    }

    // getters and setters

    public Category getCategory() {
        return this.category;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }
}
