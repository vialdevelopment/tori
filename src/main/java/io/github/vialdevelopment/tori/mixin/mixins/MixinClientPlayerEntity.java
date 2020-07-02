package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.util.Logger;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessage(String message, CallbackInfo ci) {

        final String prefix = Tori.INSTANCE.commandManager.PREFIX;

        if (message.startsWith(prefix)) {
            final String commandInput = message.replaceFirst(prefix, "");
            // if it did not successfully go to a command, go to the module commands
            if (!Tori.INSTANCE.commandManager.dispatchRunnable(commandInput)) {
                Logger.log("nothin found D:");
            }
            ci.cancel();
        }
    }
}
