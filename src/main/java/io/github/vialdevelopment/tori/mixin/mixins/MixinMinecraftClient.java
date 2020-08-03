package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.TickEvent;
import io.github.vialdevelopment.tori.mixin.duck.MinecraftClientDuck;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient implements MinecraftClientDuck {

    @Final
    @Accessor(value = "session")
    public abstract void setSession(Session session);

    @Inject(method = "close", at = @At("HEAD"))
    private void shutdownMinecraftApplet(CallbackInfo ci) {
        Tori.INSTANCE.configManager.writeConfig();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        Tori.INSTANCE.eventManager.dispatch(new TickEvent());
    }
}
