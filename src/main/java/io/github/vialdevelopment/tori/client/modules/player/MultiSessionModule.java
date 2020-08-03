package io.github.vialdevelopment.tori.client.modules.player;


import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Module;
import io.github.vialdevelopment.tori.client.events.PacketEvent;
import io.github.vialdevelopment.tori.mixin.duck.HandshakeC2SPacketDuck;
import io.github.vialdevelopment.tori.util.SessionUtil;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Session;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;

import java.util.HashMap;

public class MultiSessionModule extends Module {
    public MultiSessionModule() {
        super("MultiSession", "Allows the use of several sessions at once maybe", Category.PLAYER);
    }

    private String ip = "127.0.0.1";

    private int port = 8080;

    private final HashMap<ClientPlayerEntity, Session> sessionMap = new HashMap<>();

    @Attend
    private final Attender<PacketEvent.Out> packetEventAttender = new Attender<>(PacketEvent.Out.class, event -> {
        if (event.packet instanceof HandshakeC2SPacket) {
            final HandshakeC2SPacketDuck packet = (HandshakeC2SPacketDuck) event.packet;
            this.ip = packet.getAddress();
            this.port = packet.getPort();
        }
    });

    @Override
    public void run(String[] args) {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("switch")) {
                ClientPlayerEntity entity = null;

                for (ClientPlayerEntity clientPlayerEntity : this.sessionMap.keySet()) {
                    if (clientPlayerEntity.getName().asString().equalsIgnoreCase(args[1])) {
                        entity = clientPlayerEntity;
                        break;
                    }
                }


                SessionUtil.setSession(this.sessionMap.get(entity));
                mc.player = entity;
                return;
            }
        }

        if (args.length == 3) {
            if (args[0].equals("login")) {
                if (!this.sessionMap.containsKey(mc.player)) this.sessionMap.put(mc.player, mc.getSession());

                final Session session = SessionUtil.createSession(args[1], args[2]);

                if (session != null) SessionUtil.setSession(session);

                mc.openScreen(new ConnectScreen(null, mc, this.ip, this.port));
            }
        }
    }
}
