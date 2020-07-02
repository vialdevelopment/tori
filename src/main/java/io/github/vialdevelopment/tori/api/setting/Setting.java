package io.github.vialdevelopment.tori.api.setting;

import io.github.vialdevelopment.tori.util.Logger;

public class Setting<T> {
    /**
     * the module name, public so you can get it and possibly change it if wanted
     */
    private final String name;

    /**
     * the value, made public for easy modification
     */
    private T value;

    /**
     * a way to create a new instance of the setting, easily adding the name and value and such
     * @param name the setting name
     * @param value the setting value
     */
    public Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    /**
     * used to set the value and log the change
     * @param value what the value will be changed to
     */
    public void setValue(T value) {
        this.value = value;
        Logger.log(this.name + " was set to " + value);
    }

    public String getName() {
        return this.name;
    }

    public T getValue() {
        return this.value;
    }
}
