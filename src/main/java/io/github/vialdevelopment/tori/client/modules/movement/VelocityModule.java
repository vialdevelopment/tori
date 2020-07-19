package io.github.vialdevelopment.tori.client.modules.movement;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.Module;
import io.github.vialdevelopment.tori.client.events.AddEntityCollisionEvent;
import io.github.vialdevelopment.tori.client.events.PacketEvent;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

public class VelocityModule extends Module {
    public VelocityModule() {
        super("Velocity", "Stops you from taking too much knockback", Category.MOVEMENT);
    }

    @Attend
    private Attender packetInEvent = new Attender<>(PacketEvent.In.class, event -> {
        if (event.packet instanceof EntityVelocityUpdateS2CPacket) {
            event.setCanceled(true);
            return;
        }
        if (event.packet instanceof ExplosionS2CPacket) {
            event.setCanceled(true);
            //return;
        }
    });


    @Attend
    private Attender<AddEntityCollisionEvent> addEntityCollisionEvent = new Attender<>(AddEntityCollisionEvent.class, event -> {
        if (event.entity == mc.player) {
            event.x = 0;
            event.y = 0;
            event.z = 0;
        }
    });
}
