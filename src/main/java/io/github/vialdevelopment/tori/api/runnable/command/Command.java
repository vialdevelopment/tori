package io.github.vialdevelopment.tori.api.runnable.command;

import io.github.vialdevelopment.tori.api.traits.TraitDescribable;
import io.github.vialdevelopment.tori.api.traits.TraitNameable;
import io.github.vialdevelopment.tori.api.traits.TraitRunnable;

public class Command implements TraitRunnable, TraitNameable, TraitDescribable {

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
