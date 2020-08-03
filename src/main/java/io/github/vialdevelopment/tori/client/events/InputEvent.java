package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.CancellableEvent;
import io.github.vialdevelopment.tori.api.event.EventStage;
import io.github.vialdevelopment.tori.api.event.StagedEvent;
import net.minecraft.client.input.Input;

public class InputEvent extends CancellableEvent {
    public Input input;

    public InputEvent(EventStage stage) {
        this.eventStage = stage;
    }
}
