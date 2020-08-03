package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.CancellableEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class UpdateCameraEvent extends CancellableEvent {
    public Entity focusedEntity;

    public float tickDelta;

    public double x, y, z;

    public float yaw, pitch;

    public UpdateCameraEvent(Entity focusedEntity, float tickDelta, float lastCameraY, float cameraY) {
        this.focusedEntity = focusedEntity;
        this.tickDelta = tickDelta;

        this.x = MathHelper.lerp(tickDelta, focusedEntity.prevX, focusedEntity.getX());

        this.y = MathHelper.lerp(tickDelta, focusedEntity.prevY, focusedEntity.getY()) + MathHelper.lerp(tickDelta, lastCameraY, cameraY);

        this.z = MathHelper.lerp(tickDelta, focusedEntity.prevZ, focusedEntity.getZ());

        this.yaw = focusedEntity.getYaw(tickDelta);

        this.pitch = focusedEntity.getPitch(tickDelta);
    }
}
