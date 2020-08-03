package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.modules.movement.JesusModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    private void canWalkOnFluid(CallbackInfoReturnable<Boolean> cir) {
        if (JesusModule.INSTANCE.getState()) {
            if (MinecraftClient.getInstance().player != null) {
                if (this.getEntityId() == MinecraftClient.getInstance().player.getEntityId()) {
                    cir.setReturnValue(true);
                    cir.cancel();
                } else if (this.getVehicle() != null) {
                    if (this.getVehicle().getEntityId() == MinecraftClient.getInstance().player.getEntityId()) {
                        cir.setReturnValue(true);
                        cir.cancel();
                    }
                }
            }
        }
    }
}
