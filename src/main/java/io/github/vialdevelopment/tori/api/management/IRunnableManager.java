package io.github.vialdevelopment.tori.api.management;

import io.github.vialdevelopment.tori.api.runnable.IRunnable;

public interface IRunnableManager {
    default void init() {
        this.addRunnables();
    }

    void addRunnables();

    void addRunnable(IRunnable runnable);

    boolean dispatchRunnable(String message);
}
