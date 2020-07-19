package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.StagedEvent;
import net.minecraft.entity.Entity;

public class AddEntityCollisionEvent extends StagedEvent {
    public Entity entity;
    public double x;
    public double y;
    public double z;

    public AddEntityCollisionEvent(Object entity, double x, double y, double z) {
        this.entity = (Entity) entity;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
