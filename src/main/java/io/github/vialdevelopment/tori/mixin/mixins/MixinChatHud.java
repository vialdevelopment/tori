package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.mixin.duck.ChatHudDuck;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChatHud.class)
public abstract class MixinChatHud implements ChatHudDuck {
    @Invoker
    public abstract boolean invokeIsChatFocused();
}
