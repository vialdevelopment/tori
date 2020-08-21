package io.github.vialdevelopment.tori.api.traits;

public interface TraitToggleable {

    boolean getState();

    void setState(boolean state);

    default void toggle() {
        this.setState(!this.getState());
    }

    void onEnable();

    void onDisable();
}
