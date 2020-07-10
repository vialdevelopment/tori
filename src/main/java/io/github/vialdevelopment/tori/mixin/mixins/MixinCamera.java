package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.UpdateCameraEvent;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Shadow
    protected abstract void setPos(double x, double y, double z);

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Shadow
    private float lastCameraY;

    @Shadow
    private float cameraY;


    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"), cancellable = true)
    private void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        UpdateCameraEvent event = new UpdateCameraEvent(focusedEntity, tickDelta, this.lastCameraY, this.cameraY);

        Tori.INSTANCE.eventManager.dispatch(event);

        if (event.isCanceled()) {
            this.setPos(event.pos.getX(), event.pos.getY(), event.pos.getZ());

            this.setRotation(event.yaw, event.pitch);

            ci.cancel();
        }
    }
}
