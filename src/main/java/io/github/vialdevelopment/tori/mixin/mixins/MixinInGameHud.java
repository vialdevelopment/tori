package io.github.vialdevelopment.tori.mixin.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.Render2DEvent;
import net.fabricmc.fabric.impl.client.indigo.renderer.IndigoRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cats
 * @since June 4, 2020
 */
@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    private void render(MatrixStack matrixStack, float partialTicks, CallbackInfo ci) {
        RenderSystem.pushMatrix();
        final Render2DEvent event = new Render2DEvent(matrixStack, partialTicks);
        Tori.INSTANCE.eventManager.dispatch(event);
        RenderSystem.popMatrix();
    }
}
