package io.github.vialdevelopment.tori.client.events;


import io.github.vialdevelopment.tori.api.event.EventStage;
import io.github.vialdevelopment.tori.api.event.StagedEvent;

public class SendMovementPacketEvent extends StagedEvent {
    public boolean onGround;
    public float yaw;
    public float pitch;
    public double y;

    public SendMovementPacketEvent(final float yaw, final float pitch, final double y, final boolean onGround, EventStage stage) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.onGround = onGround;
        this.eventStage = stage;
    }

}
