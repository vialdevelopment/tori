package io.github.vialdevelopment.tori.api.setting.impl;

import io.github.vialdevelopment.tori.api.setting.ISetting;
import io.github.vialdevelopment.tori.api.traits.INameable;

public class Setting<T> implements ISetting<T>, INameable {

    private String name;

    private T value;

    public Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
