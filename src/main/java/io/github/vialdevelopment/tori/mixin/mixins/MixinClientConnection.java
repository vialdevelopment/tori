package io.github.vialdevelopment.tori.mixin.mixins;

import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.PacketEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Shadow
    private Channel channel;

    @Inject(at = @At("HEAD"), method = "channelRead0", cancellable = true)
    private void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet, CallbackInfo ci) {
        if (this.channel.isOpen() && packet != null) {
            PacketEvent.In event = new PacketEvent.In(packet);
            Tori.INSTANCE.eventManager.dispatch(event);
            if (event.isCanceled()) ci.cancel();
        }
    }

    @Inject( at = @At("HEAD"), method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", cancellable = true)
    private void send(Packet packet, GenericFutureListener genericFutureListener, CallbackInfo ci) {
        PacketEvent.Out event = new PacketEvent.Out(packet);
        Tori.INSTANCE.eventManager.dispatch(event);
        if (event.isCanceled()) ci.cancel();
    }
}
