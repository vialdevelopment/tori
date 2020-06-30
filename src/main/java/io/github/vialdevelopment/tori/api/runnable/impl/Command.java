package io.github.vialdevelopment.tori.api.runnable.impl;

import io.github.vialdevelopment.tori.api.runnable.IRunnable;

public class Command implements IRunnable {

    private final String name;

    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void run(String[] args) {

    }

    // getters and setters


    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }
}
