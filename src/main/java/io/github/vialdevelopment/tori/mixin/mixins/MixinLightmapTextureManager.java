package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.modules.render.BrightnessModule;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.util.math.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public class MixinLightmapTextureManager {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/Vector3f;lerp(Lnet/minecraft/client/util/math/Vector3f;F)V", ordinal = 5))
    private void lerpWrapper(Vector3f vector3f, Vector3f vector, float delta) {
        vector3f.lerp(vector, BrightnessModule.INSTANCE.getState() ? 1000f : delta);
    }
}
