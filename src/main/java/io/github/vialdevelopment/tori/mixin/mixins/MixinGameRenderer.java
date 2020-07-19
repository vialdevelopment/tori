package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.RenderScreenObstructionEvent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void bobViewWhenHurt(MatrixStack matrixStack, float tickDelta, CallbackInfo ci) {
        final RenderScreenObstructionEvent event = new RenderScreenObstructionEvent(RenderScreenObstructionEvent.Type.HURT);
        Tori.INSTANCE.eventManager.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
