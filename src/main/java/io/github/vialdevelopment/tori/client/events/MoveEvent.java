package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.CancellableEvent;

public class MoveEvent extends CancellableEvent {
    public double x, y, z;

    public MoveEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}