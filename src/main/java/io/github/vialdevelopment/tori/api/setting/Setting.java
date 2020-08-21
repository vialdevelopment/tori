package io.github.vialdevelopment.tori.api.setting;

import io.github.vialdevelopment.tori.api.traits.TraitNameable;
import io.github.vialdevelopment.tori.api.traits.TraitValued;

public class Setting<T> implements TraitValued<T>, TraitNameable {

    private final String name;

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
