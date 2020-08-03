package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.mixin.duck.HandshakeC2SPacketDuck;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandshakeC2SPacket.class)
public abstract class MixinHandshakeC2SPacket implements HandshakeC2SPacketDuck {
    @Accessor(value = "address")
    public abstract String getAddress();

    @Accessor(value = "port")
    public abstract int getPort();
}
