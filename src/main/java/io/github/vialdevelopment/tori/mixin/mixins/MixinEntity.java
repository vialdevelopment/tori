package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.AddEntityCollisionEvent;
import io.github.vialdevelopment.tori.mixin.duck.EntityDuck;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class MixinEntity implements EntityDuck {

    @Accessor(value = "inNetherPortal")
    public abstract void setInNetherPortal(boolean inNetherPortal);

    @Redirect(method =  "pushAwayFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    private void addVelocityWrapper(Entity entity, double x, double y, double z) {
        final AddEntityCollisionEvent event = new AddEntityCollisionEvent(entity, x, y, z);
        Tori.INSTANCE.eventManager.dispatch(event);

        event.entity.addVelocity(event.x, event.y, event.z);
    }

    @Redirect(method =  "updateMovementInFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;getVelocity(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d getVelocityWrapper(FluidState fluidState, BlockView world, BlockPos pos) {

        final Vec3d flow = fluidState.getVelocity(world, pos);
        AddEntityCollisionEvent event = new AddEntityCollisionEvent(this, flow.x, flow.y, flow.z);
        Tori.INSTANCE.eventManager.dispatch(event);

        return new Vec3d(event.x, event.y, event.z);
    }
}
