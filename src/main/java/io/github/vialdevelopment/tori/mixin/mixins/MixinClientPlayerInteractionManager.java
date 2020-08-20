package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.mixin.duck.ClientPlayerInteractionManagerDuck;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager implements ClientPlayerInteractionManagerDuck {
    @Accessor(value = "currentBreakingProgress")
    public abstract float getCurrentBreakingProgress();

    @Accessor(value = "currentBreakingProgress")
    public abstract void setCurrentBreakingProgress(float currentBreakingProgress);
}
