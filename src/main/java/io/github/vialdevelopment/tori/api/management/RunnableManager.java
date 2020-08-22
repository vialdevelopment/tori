package io.github.vialdevelopment.tori.api.management;

import io.github.vialdevelopment.tori.api.traits.TraitRunnable;

public interface RunnableManager {
    default void init() {
        this.addRunnables();
    }

    void addRunnables();

    void addRunnable(TraitRunnable runnable);

    boolean dispatchRunnable(String message);
}
