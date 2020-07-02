package io.github.vialdevelopment.tori.api.runnable.toggleable;

public interface IToggleable {

    boolean getState();

    void setState(boolean state);

    default void toggle() {
        this.setState(!this.getState());
    }

    void onEnable();

    void onDisable();
}
