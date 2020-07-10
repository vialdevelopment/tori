package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.GetItemCooldownEvent;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCooldownManager.class)
public class MixinItemCooldownManager {
    @Inject(method = "getCooldownProgress", at = @At("HEAD"), cancellable = true)
    private void getCooldownProgress(Item item, float partialTicks, CallbackInfoReturnable<Float> cir) {
        final GetItemCooldownEvent event = new GetItemCooldownEvent();
        Tori.INSTANCE.eventManager.dispatch(event);
        if (event.coolDown != null) {
            cir.setReturnValue(event.coolDown);
            cir.cancel();
        }
    }
}
