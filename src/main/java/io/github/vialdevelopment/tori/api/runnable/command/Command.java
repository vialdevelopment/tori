package io.github.vialdevelopment.tori.api.runnable.command;

import io.github.vialdevelopment.tori.api.traits.IDescribable;
import io.github.vialdevelopment.tori.api.traits.INameable;
import io.github.vialdevelopment.tori.api.traits.IRunnable;

public class Command implements IRunnable, INameable, IDescribable {

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


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
