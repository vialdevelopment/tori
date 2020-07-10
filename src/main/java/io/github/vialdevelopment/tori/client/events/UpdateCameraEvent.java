package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.CancellableEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class UpdateCameraEvent extends CancellableEvent {
    public Entity focusedEntity;

    public float tickDelta;

    public Vec3d pos;

    public float yaw, pitch;

    public UpdateCameraEvent(Entity focusedEntity, float tickDelta, float lastCameraY, float cameraY) {
        this.focusedEntity = focusedEntity;
        this.tickDelta = tickDelta;

        final double x = MathHelper.lerp(tickDelta, focusedEntity.prevX, focusedEntity.getX());

        final double y = MathHelper.lerp(tickDelta, focusedEntity.prevY, focusedEntity.getY()) + MathHelper.lerp(tickDelta, lastCameraY, cameraY);

        final double z = MathHelper.lerp(tickDelta, focusedEntity.prevZ, focusedEntity.getZ());

        this.pos = new Vec3d(x, y, z);

        this.yaw = focusedEntity.getYaw(tickDelta);

        this.pitch = focusedEntity.getPitch(tickDelta);
    }

}
