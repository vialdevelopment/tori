package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.CancellableEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends CancellableEvent {
    public final Packet packet;

    public PacketEvent(Packet packet) {
        super();
        this.packet = packet;
    }


    public static class In extends PacketEvent {
        public In(Packet packet) {
            super(packet);
        }
    }

    public static class Out extends PacketEvent {
        public Out(Packet packet) {
            super(packet);
        }
    }
}
