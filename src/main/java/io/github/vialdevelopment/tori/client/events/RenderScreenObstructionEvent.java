package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.CancellableEvent;

public class RenderScreenObstructionEvent extends CancellableEvent {
    public final Type overlay;

    public RenderScreenObstructionEvent(Type overlay) {
        this.overlay = overlay;
    }

    public enum Type {
        FLUID,
        FIRE,
        HURT,
        BOSS
    }
}
